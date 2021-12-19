package validator;

import utils.Violation;
import validator.impl.ValidationProvider;

import java.util.Collection;

public interface Validatable {
    default Collection<Violation> validate(){
        ValidationProvider validationProvider = ValidationProvider.getInstance();
        return validationProvider.validate(this);
    }
}
