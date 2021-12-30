package validator.impl;

import violation.ViolationContext;

import java.util.Collection;

public class CollectionValidatorChain<T> extends CompositeValidatorChain<T>{
    @Override
    public void processValidation(ValidateObject<T> value, ViolationContext context) {
        if (Collection.class.isAssignableFrom(value.getType())){
            Collection<T> collection = (Collection<T>) value.getValue();
            collection.forEach(
                    element -> {
                        super.processValidation(new ValidateObject<T>((Class<T>) element.getClass(), element), context);
                    }
            );
        }
    }

}
