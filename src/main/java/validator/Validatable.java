package validator;

import violation.Violation;
import validator.impl.ValidationProvider;

import java.util.Collection;

public interface Validatable {
    default Collection<Violation> validate(){
        ValidationProvider validationProvider = ValidationProvider.getInstance();
        var holder = validationProvider.resolveObject(this.getClass());
        return holder.validate(this);
    }
}
