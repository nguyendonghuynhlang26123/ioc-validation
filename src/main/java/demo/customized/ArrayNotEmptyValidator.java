package demo.customized;

import validator.impl.BaseValidator;
import validator.Validator;

import java.util.Collection;

public class ArrayNotEmptyValidator extends BaseValidator<ArrayNotEmpty, Collection> {
    @Override
    public void onInit(ArrayNotEmpty arrayNotEmpty) {}

    @Override
    public boolean isValid(Collection value) {
        return value.size() > 0;
    }

    @Override
    public Validator<Collection> cloneValidator() {
        return new ArrayNotEmptyValidator();
    }
}
