package annotations.impl;

import annotations.NotEmpty;
import validator.Validator;
import validator.impl.BaseValidator;

public class NotEmptyValidator extends BaseValidator<NotEmpty, String> {

    @Override
    public void initialize(NotEmpty notEmpty) {}

    @Override
    public boolean isValid(String value) {
        return !value.isEmpty();
    }

}
