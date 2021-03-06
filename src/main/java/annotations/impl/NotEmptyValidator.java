package annotations.impl;

import annotations.NotEmpty;
import validator.Validator;
import validator.impl.BaseValidator;

public class NotEmptyValidator extends BaseValidator<NotEmpty, String> {
    public NotEmptyValidator(){}

    @Override
    public void onInit(NotEmpty notEmpty) {}

    @Override
    public boolean isValid(String value) {
        return value==null || !value.isEmpty();
    }

    @Override
    public Validator<String> cloneValidator() {
        return new NotEmptyValidator(this);
    }

    protected NotEmptyValidator(NotEmptyValidator other) {
        super(other);
    }
}
