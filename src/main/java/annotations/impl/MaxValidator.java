package annotations.impl;

import annotations.Max;
import utils.Violation;
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
    public Class<Number> acceptType() {
        return Number.class;
    }

    @Override
    public Violation violationBuilder(Number value) {
        return new Violation(value,"Value should not be greater than " + this.max);
    }

    @Override
    public Validator<Number> cloneValidator() {
        return new MaxValidator(this.max);
    }

    @Override
    public void initialize(Max max) {
        this.max = max.value();
    }
}
