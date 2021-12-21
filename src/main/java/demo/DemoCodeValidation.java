package demo;

import builder.ValidatorBuilder;
import utils.exceptions.ValidationException;

public class DemoCodeValidation {
    public void demo(String input){
        try{
            new ValidatorBuilder<>(String.class)
                    .notEmpty()
                    .length(6)
                    .build().validate(input);
        } catch (ValidationException e){
            // TODO: catch errors here???
            e.printStackTrace();
        }
    }
}
