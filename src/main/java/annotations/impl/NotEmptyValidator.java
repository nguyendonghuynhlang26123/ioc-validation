package annotations.impl;

import annotations.NotEmpty;
import validator.Validator;
import validator.BaseValidator;

public class NotEmptyValidator extends BaseValidator<NotEmpty, String> {
    public NotEmptyValidator(){}

    @Override
    public void onInit(NotEmpty notEmpty) {}

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
        return new NotEmptyValidator(this);
    }

    protected NotEmptyValidator(NotEmptyValidator other) {
        super(other);
    }
}
