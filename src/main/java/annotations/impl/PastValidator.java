package annotations.impl;

import annotations.Past;
import validator.Validator;
import validator.impl.BaseValidator;

import java.time.LocalDate;

public class PastValidator extends BaseValidator<Past, LocalDate> {
    public PastValidator(){};
    public PastValidator(PastValidator past){
        super(past);
    }
    @Override
    public boolean isValid(LocalDate value) {
        return value==null||value.isBefore(LocalDate.now());
    }

    @Override
    public Validator<LocalDate> cloneValidator() {
        return new PastValidator(this);
    }

    @Override
    public void onInit(Past past) {

    }
}
