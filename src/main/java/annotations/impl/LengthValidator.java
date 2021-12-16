package annotations.impl;

import annotations.Length;
import validator.Validator;
import validator.impl.BaseValidator;

public class LengthValidator extends BaseValidator<Length,String> {
    int min;
    int max;

    @Override
    public void initialize(Length length) {
        this.max = length.max();
        this.min = length.min();
    }

    @Override
    public boolean isValid(String value) {
        return value.length() >= min && value.length() <= max;
    }

}
