package annotations.impl;

import annotations.NotEmpty;
import violation.Violation;
import validator.Validator;
import validator.impl.BaseValidator;

public class NotEmptyValidator extends BaseValidator<NotEmpty, String> {
    @Override
    public void initialize(NotEmpty notEmpty) {}

    @Override
    public boolean isValid(String value) {
        return !value.isEmpty();
    }

    @Override
    public Class<String> acceptType() {
        return String.class;
    }

    @Override
    public Violation violationBuilder(String value) {
        return new Violation(value,"Empty value");
    }

    @Override
    public Validator<String> cloneValidator() {
        return new NotEmptyValidator();
    }
}
