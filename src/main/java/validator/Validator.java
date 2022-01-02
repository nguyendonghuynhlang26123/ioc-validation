package validator;

import utils.exceptions.UnexpectedTypeException;
import utils.ValidateObject;
import violation.ViolationContext;

public interface Validator<T> {
    boolean isValid(T value);
    void processValidation(ValidateObject<T> object, ViolationContext context) throws UnexpectedTypeException;
    boolean isValidType(Class<T> clazz);

    Validator<T> cloneValidator();
    Validator<T> setNext(Validator<T> next);
    Validator<T> getNext();
}
