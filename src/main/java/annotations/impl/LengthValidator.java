package annotations.impl;

import annotations.Length;
import violation.Violation;
import validator.Validator;
import validator.impl.BaseValidator;

public class LengthValidator extends BaseValidator<Length,String> {
    int min;
    int max;

    @Override
    public void onInit(Length length) {
        this.max = length.max();
        this.min = length.min();
    }

    public LengthValidator(){}

    public LengthValidator(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public LengthValidator(LengthValidator other){
        super(other);
        this.min = other.min;
        this.max = other.max;
    }

    @Override
    public boolean isValid(String value) {
        return value.length() >= min && value.length() <= max;
    }

    @Override
    public Class<String> supportType() {
        return String.class;
    }

    @Override
    public String violationMessage(String value) {
        return value +  " not have length from "+min+" to "+max;
    }

    @Override
    public Validator<String> cloneValidator() {
        return new LengthValidator(this);
    }
}
