package demo;

import utils.exceptions.ValidationException;
import validator.Validatable;
import validator.ValidatorHolder;
import validator.impl.ValidationProvider;

public class DemoCodeValidation {
    public void demo(String input){
        try{
            ValidatorHolder<String> validatable = ValidationProvider.getInstance().<String>createSingleValidatorBuilder()
                    .length(6)
                    .buildValidatable();
            validatable.validate(input);
        } catch (ValidationException e){
            // TODO: catch errors here???
            e.printStackTrace();
        }
    }
}
