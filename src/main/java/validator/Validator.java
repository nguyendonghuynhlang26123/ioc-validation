package validator;

import violation.Violation;
import utils.exceptions.UnexpectedTypeException;
import violation.ViolationContext;

public interface Validator<T> {
    boolean isValid(T value);
    void processValidation(T value, ViolationContext context) throws UnexpectedTypeException;
    Class<T> supportType();

    Validator<T> cloneValidator();
    Validator<T> setNext(Validator<T> next);
    Validator<T> getNext();
}
