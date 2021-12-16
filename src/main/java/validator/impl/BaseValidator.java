package validator.impl;

import validator.Validator;

import java.lang.annotation.Annotation;

public abstract class BaseValidator<Ctx extends Annotation,T> implements Validator<Ctx ,T> {
    private Validator<? extends Annotation, T> next;

    @Override
    public void setNext(Validator<? extends Annotation, T> validator) {
        next = validator;
    }

    @Override
    public Validator<? extends Annotation, T> getNext() {
        return next;
    }

    @Override
    public final boolean validate(T value) {
        return next == null || next.isValid(value);
    }
}
