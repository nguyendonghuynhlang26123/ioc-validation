package annotations.impl;

import annotations.Length;
import utils.Validator;

public class LengthValidatorImpl implements Validator<Length,String>  {
    int min;
    int max;

    @Override
    public void initialize(Length length) {
        this.max = length.max();
        this.min = length.min();
    }

    @Override
    public boolean isValid(String value) {
        return value.length() >= min && value.length() <= max;
    }
}
