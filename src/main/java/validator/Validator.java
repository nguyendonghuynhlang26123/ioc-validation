package validator;

import utils.Violation;
import utils.exceptions.InvalidTypeException;

public interface Validator<T> {
    boolean isValid(T value);
    Violation validate(T value) throws InvalidTypeException;
    Validator<T> setNext(Validator<T> next);
    Validator<T> getNext();
    Class<T> acceptType();
    Violation violationBuilder(T value);
    Validator<T> cloneValidator();
}
