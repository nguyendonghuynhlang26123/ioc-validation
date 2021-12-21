package validator;

import violation.Violation;
import validator.impl.ValidationProvider;

import java.util.Collection;

public interface Validatable {
    default Collection<Violation> validate(){
        ValidationProvider validationProvider = ValidationProvider.getInstance();
        var ctx = validationProvider.resolveObject(this);
        return ctx.validate();
    }
}
