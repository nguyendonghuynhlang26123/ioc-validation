package annotations.impl;

import annotations.Length;
import annotations.NotEmpty;
import utils.Validator;

public class NotEmptyValidatorImpl implements Validator<NotEmpty, String> {
    @Override
    public boolean isValid(String value) {
        return !value.isEmpty();
    }

    @Override
    public void initialize(NotEmpty ctx) {
        //Skip this
    }
}
