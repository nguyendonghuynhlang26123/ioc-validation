package demo;

import builder.ValidatableBuilder;
import utils.exceptions.ValidationException;
import validator.Validatable;
import validator.impl.ValidationProvider;

public class DemoCodeValidation {
    public void demo(String input){
        try{
            Validatable validatable = ValidationProvider.getInstance().createValidatorBuilder(String.class)
                    .length(6)
                    .buildValidatable(input);
            validatable.validate();
        } catch (ValidationException e){
            // TODO: catch errors here???
            e.printStackTrace();
        }
    }
}
