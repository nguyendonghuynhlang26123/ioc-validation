package annotations.impl;

import annotations.NotEmpty;
import utils.exceptions.InvalidValueException;
import utils.exceptions.ViolationException;
import validator.Validator;
import validator.impl.BaseValidator;

import java.util.Collection;

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
    public ViolationException exceptionBuilder(String value) {
        return new InvalidValueException(this.getClass().getSimpleName()+": "+value+" is empty");
    }

    @Override
    public Validator<NotEmpty, String> cloneValidator() {
        return new NotEmptyValidator();
    }
}
