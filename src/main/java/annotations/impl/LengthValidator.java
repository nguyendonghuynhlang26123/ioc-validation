package annotations.impl;

import annotations.Length;
import utils.Violation;
import utils.exceptions.InvalidValueException;
import utils.exceptions.ViolationException;
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

    public LengthValidator(){}

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

    @Override
    public Violation violationBuilder(String value) {
        return new Violation(value,
                value+" has length violate min = "+min+", max = "+max);
    }

    @Override
    public Validator<String> cloneValidator() {
        return new LengthValidator(this.min, this.max);
    }
}
