package validator;

import violation.Violation;
import utils.exceptions.UnexpectedTypeException;

import java.util.Collection;

public interface Validator<T> {
    boolean isValid(T value);
    void processValidation(T value, Collection<Violation> violations) throws UnexpectedTypeException;
    Class<T> supportType();
    String violationMessage(T value);

    Validator<T> cloneValidator();
    Validator<T> setNext(Validator<T> next);
    Validator<T> getNext();
}
