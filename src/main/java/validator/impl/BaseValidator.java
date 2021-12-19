package validator.impl;

import utils.exceptions.InvalidTypeException;
import utils.exceptions.InvalidValueException;
import utils.exceptions.ViolationException;
import validator.Validator;

import java.lang.annotation.Annotation;
import java.util.Collection;

public abstract class BaseValidator<Ctx extends Annotation,T> implements Validator<Ctx ,T> {
    private Validator<? extends Annotation, T> next;

    @Override
    public Validator<?, T> setNext(Validator<? extends Annotation, T> validator) {
        next = validator;
        return next;
    }

    @Override
    public Validator<? extends Annotation, T> getNext() {
        return next;
    }

    @Override
    public final void validate(T value) throws ViolationException {
//        System.out.println("Validate " + value + " of type " + acceptType().getSimpleName());
        Class<?> myType = acceptType();
        if (!value.getClass().equals(myType)){
            throw new InvalidTypeException(this.getClass().getSimpleName()+" invalid type access");
        }
        if (!this.isValid(value)){
            throw exceptionBuilder(value);
        }
        if( next != null){
            next.validate(value);
        }
    }
}
