package validator;


import java.lang.annotation.Annotation;
import java.util.Collection;

public interface Validator<Ctx extends Annotation,T> {
    void initialize(Ctx ctx);
    boolean isValid(T value);
    boolean validate(T value);
    void setNext(Validator<? extends Annotation, T> next);
    Validator<?,T> getNext();
    Class<T> acceptType();
}
