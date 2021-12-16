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

    public LengthValidator() {
    }

    public LengthValidator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean isValid(String value) {
        return value.length() >= min && value.length() <= max;
    }

    @Override
    public Class<String> acceptType() {
        return String.class;
    }
}
