package utils;

import java.lang.annotation.Annotation;

public interface Validator<Ctx extends Annotation,T> {
    void initialize(Ctx ctx);
    boolean isValid(T value);
}
