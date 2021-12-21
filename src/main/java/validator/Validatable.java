package validator;

import violation.Violation;
import validator.impl.ValidationProvider;

import java.util.Collection;

public interface Validatable {
    default void validate(){
        ValidationProvider validationProvider = ValidationProvider.getInstance();
        var ctx = validationProvider.resolveObject(this);
        ctx.validate();
    }
}
