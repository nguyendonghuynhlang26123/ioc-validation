package demo;

import annotations.impl.LengthValidator;
import annotations.impl.NotEmptyValidator;
import utils.exceptions.ViolationException;
import validator.ValidatorChain;

public class DemoCodeValidation {
    public void demo(String input){
        try{
            new ValidatorChain<String>()
                    .add(new LengthValidator(1,10))
                    .add(new NotEmptyValidator())
                    .validate(input);
        } catch (ViolationException e){
            // TODO: catch errors here???
            e.printStackTrace();
        }
    }
}