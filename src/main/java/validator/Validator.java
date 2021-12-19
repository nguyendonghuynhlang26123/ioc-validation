package validator;

import utils.Violation;
import utils.exceptions.InvalidTypeException;
import java.lang.annotation.Annotation;

public interface Validator<Ctx extends Annotation,T> {
    void initialize(Ctx ctx);
    boolean isValid(T value);
    Violation validate(T value) throws InvalidTypeException;
    Validator<?, T> setNext(Validator<? extends Annotation, T> next);
    Validator<?,T> getNext();
    Class<T> acceptType();
    Violation violationBuilder(T value);
    Validator<Ctx, T> cloneValidator();
}
