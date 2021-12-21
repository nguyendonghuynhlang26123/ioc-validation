package validator;

import violation.Violation;
import utils.exceptions.UnexpectedTypeException;

public interface Validator<T> {
    boolean isValid(T value);
    Violation validate(T value) throws UnexpectedTypeException;
    Class<T> supportType();
    String violationMessage(T value);

    Validator<T> cloneValidator();
    Validator<T> setNext(Validator<T> next);
    Validator<T> getNext();
}
