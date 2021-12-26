package validator;

import utils.exceptions.UnexpectedTypeException;
import validator.impl.ValidateObject;
import violation.ViolationContext;

public interface Validator<T> {
    boolean isValid(T value);
    void processValidation(ValidateObject<T> object, ViolationContext context) throws UnexpectedTypeException;
    Class<T> supportType();

    Validator<T> cloneValidator();
    Validator<T> setNext(Validator<T> next);
    Validator<T> getNext();
}
