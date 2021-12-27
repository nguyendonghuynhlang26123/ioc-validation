package annotations.impl;

import annotations.Max;
import validator.Validator;
import validator.impl.BaseValidator;

public class MaxValidator extends BaseValidator<Max, Number> {
    long max;

    public MaxValidator() {}

    public MaxValidator(long value) {
        this.max = value;
    }

    public MaxValidator(MaxValidator other){
        super(other);
        this.max = other.max;
    }

    @Override
    public boolean isValid(Number value) {
        return value==null || value.doubleValue() <= this.max;
    }

    @Override
    public Validator<Number> cloneValidator() {
        return new MaxValidator(this);
    }

    @Override
    public void onInit(Max max) {
        this.max = max.value();
    }
}
