package validator.impl;

import violation.ViolationContext;

import java.util.Collection;

public class CollectionValidatorChain<T> extends CompositeValidatorChain<T>{
    @Override
    public void processValidation(ValidateObject<T> value, ViolationContext context) {
        if (Collection.class.isAssignableFrom(value.getType())){
            Collection collection = (Collection) value.getValue();
            collection.forEach(
                    element -> {
                        ValidateObject validateObject = new ValidateObject(element.getClass(), element);
                        chains.forEach((k, chainList) -> {
                            chainList.forEach(chain -> chain.processValidation(validateObject, context));
                        });
                    }
            );
        }
    }
}
