package validator;

import validator.impl.ValidationProvider;

public interface Validatable {
    default void validate(){
        ValidationProvider validationProvider = ValidationProvider.getInstance();
        validationProvider.validate(this);
    }
}
