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
    public Class<String> supportType() {
        return String.class;
    }

    @Override
    public String violationMessage(String value) {
        return "Value should not be empty";
    }

    @Override
    public Validator<String> cloneValidator() {
        return new NotEmptyValidator();
    }
}
