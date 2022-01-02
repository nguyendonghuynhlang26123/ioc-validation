package validator.impl;

import utils.AddChainRequest;
import utils.ValidateObject;
import utils.exceptions.ChainBuilderException;
import validator.ChainPrototype;
import violation.ViolationContext;

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
            collection.forEach(
                element -> childChain.processValidation(new ValidateObject(element.getClass(), element), context)
            );
        }
    }

    @Override
    public void validateAndThrow(T value) {

    }

    @Override
    public ChainPrototype<T> addChain(AddChainRequest<T> chainRequest) {
        if (childChain != null) {
            throw new ChainBuilderException("Child chain existed");
        }
        childChain = chainRequest.getChain();
        return this;
    }
}
