package validator.impl;

import utils.exceptions.ViolationException;
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
    public final void validate(T value) throws ViolationException {
//        System.out.println("Validate " + value + " of type " + acceptType().getSimpleName());
        Class<?> myType = acceptType();
        if (!value.getClass().equals(myType)){
            // TODO: throw exception here???
            throw new ViolationException();
        }
        if (!this.isValid(value)){
            // TODO: throw exception here???
            throw new ViolationException();
        }
        if( next != null){
            next.validate(value);
        }
    }
}
