package validator.impl;

import validator.Validator;

import java.lang.annotation.Annotation;
import java.util.Collection;

public abstract class BaseValidator<Ctx extends Annotation,T> implements Validator<Ctx ,T> {
    private Validator<? extends Annotation, T> next;

    public BaseValidator() {
    }

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
//        System.out.println("Validate " + value + " of type " + acceptType().getSimpleName());
        Class<?> myType = acceptType();
        if (!value.getClass().equals(myType)) return false; // TODO: throw exception here???
        if (!this.isValid(value)) return false; // TODO: throw exception here???
        return next == null || next.isValid(value);
    }
}
