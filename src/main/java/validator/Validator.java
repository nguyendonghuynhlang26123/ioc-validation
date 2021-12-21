package validator;

import violation.Violation;
import utils.exceptions.UnexpectedTypeException;

public interface Validator<T> {
    boolean isValid(T value);
    Violation validate(T value) throws UnexpectedTypeException;
    Validator<T> setNext(Validator<T> next);
    Validator<T> getNext();
    Class<T> acceptType();
    Violation violationBuilder(T value);
    Validator<T> cloneValidator();
}
