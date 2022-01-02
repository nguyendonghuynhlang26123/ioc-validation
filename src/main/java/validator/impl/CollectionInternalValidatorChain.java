package validator.impl;

import utils.AddChainRequest;
import utils.ValidateObject;
import utils.exceptions.ChainBuilderException;
import validator.ChainPrototype;
import violation.ViolationContext;

import java.util.Arrays;
import java.util.Collection;

public class CollectionInternalValidatorChain<T> implements ChainPrototype<T> {
    ChainPrototype childChain;

    public CollectionInternalValidatorChain(){}

    @Override
    public ChainPrototype<T> cloneChain() {
        CollectionInternalValidatorChain<T> clone = new CollectionInternalValidatorChain<>();
        clone.implicitAddChain(childChain.cloneChain());
        return clone;
    }

    private void implicitAddChain(ChainPrototype chain){
        childChain = chain;
    }

    @Override
    public void processValidation(ValidateObject<T> value, ViolationContext context) {
        if (Collection.class.isAssignableFrom(value.getType())){
            Collection<?> collection = (Collection<?>) value.getValue();
            Class<?> elementType = Object.class;
            for( var element: collection) {
                if (element != null) elementType = element.getClass();
                childChain.processValidation(new ValidateObject(elementType, element), context);
            };
        }
    }

    @Override
    public void validateAndThrow(T value) {

    }

    @Override
    public ChainPrototype<T> addChain(AddChainRequest<T> chainRequest) {
        if (childChain != null) {
            try {
                childChain.addChain(chainRequest);
            } catch (NoSuchMethodException| ChainBuilderException e){
                CollectionValidatorChain collectionChain = new CollectionValidatorChain<>();
                collectionChain.addChain(new AddChainRequest<>(childChain));
                collectionChain.addChain(chainRequest);
                childChain = collectionChain;
            }
        } else {
            childChain = chainRequest.getChain();
        }
        return this;
    }

    @Override
    public boolean isEmpty() {
        return childChain==null;
    }
}
