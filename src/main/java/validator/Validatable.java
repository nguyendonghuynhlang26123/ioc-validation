package validator;

import utils.exceptions.ValidationException;
import violation.Violation;
import validator.impl.ValidationProvider;

import java.util.Collection;
import java.util.Collections;

public interface Validatable {
    default Collection<Violation> validate(){
        ValidationProvider validationProvider = ValidationProvider.getInstance();
        try{
            var holder = validationProvider.wrapChain(this.getClass());
            return holder.validate(this);
        } catch (ValidationException e){
            System.out.println(e.getMessage());
            return Collections.EMPTY_LIST;
        }
    }
}
