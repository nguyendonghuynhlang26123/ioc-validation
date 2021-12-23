package annotations.impl;

import annotations.Max;
import violation.Violation;
import validator.Validator;
import validator.impl.BaseValidator;

public class MaxValidator extends BaseValidator<Max, Number> {
    long max;

    public MaxValidator() {
    }

    public MaxValidator(long value) {
        this.max = value;
    }

    @Override
    public boolean isValid(Number value) {
        return value.doubleValue() <= this.max;
    }

    @Override
    public Class<Number> supportType() {
        return Number.class;
    }

    @Override
    public String violationMessage(Number value) {
        return "Value should not be greater than " + this.max;
    }

    @Override
    public Validator<Number> cloneValidator() {
        return new MaxValidator(this.max);
    }

    @Override
    public void onInit(Max max) {
        this.max = max.value();
    }
}
