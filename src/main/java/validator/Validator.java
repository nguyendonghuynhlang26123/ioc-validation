package validator;


import utils.exceptions.ViolationException;

import java.lang.annotation.Annotation;
import java.util.Collection;

public interface Validator<Ctx extends Annotation,T> {
    void initialize(Ctx ctx);
    boolean isValid(T value);
    void validate(T value) throws ViolationException;
    void setNext(Validator<? extends Annotation, T> next);
    Validator<?,T> getNext();
    Class<T> acceptType();
    ViolationException exceptionBuilder(T value);
}
