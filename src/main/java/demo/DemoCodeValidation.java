package demo;

import builder.ValidatableBuilder;
import utils.exceptions.ValidationException;
import validator.Validatable;

public class DemoCodeValidation {
    public void demo(String input){
        try{
            Validatable validatable = new ValidatableBuilder<>(String.class)
                    .notEmpty()
                    .length(6)
                    .buildValidatable(input);
            validatable.validate();
        } catch (ValidationException e){
            // TODO: catch errors here???
            e.printStackTrace();
        }
    }
}
