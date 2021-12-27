package annotations.impl;

import annotations.NotNull;
import validator.impl.BaseValidator;
import validator.Validator;

public class NotNullValidator extends BaseValidator<NotNull, Object> {
    public NotNullValidator(){}

    @Override
    public void onInit(NotNull notNull) {}

    @Override
    public boolean isValid(Object value) {
        return value!=null;
    }

    @Override
    public Validator<Object> cloneValidator() {
        return new NotNullValidator(this);
    }

    protected NotNullValidator(NotNullValidator other) {
        super(other);
    }
}
