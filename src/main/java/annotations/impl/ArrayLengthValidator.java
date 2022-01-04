package annotations.impl;

import annotations.ArrayLength;
import validator.Validator;
import validator.impl.BaseValidator;

public class ArrayLengthValidator extends BaseValidator<ArrayLength,String> {
    int min;
    int max;

    public ArrayLengthValidator(){}
    public  ArrayLengthValidator(int min, int max){
        this.min=min;
        this.max=max;
    }
    public ArrayLengthValidator(ArrayLengthValidator other){
        super(other);
        this.min=other.min;
        this.max=other.max;
    }
    @Override
    public boolean isValid(String value) {
        return value==null || (value.length() >= min && value.length() <= max);
    }

    @Override
    public Validator<String> cloneValidator() {
        return new ArrayLengthValidator(this);
    }

    @Override
    public void onInit(ArrayLength arrayLength) {
        this.max=arrayLength.max();
        this.min=arrayLength.min();
    }
}
