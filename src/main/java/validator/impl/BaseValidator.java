package validator.impl;

import utils.Violation;
import utils.exceptions.InvalidTypeException;
import validator.Validator;

import java.lang.annotation.Annotation;

public abstract class BaseValidator<Ctx extends Annotation,T> implements Validator<T> {
    private Validator<T> next;

    abstract public void initialize(Ctx ctx);

    @Override
    public Validator<T> setNext(Validator<T> validator) {
        next = validator;
        return next;
    }

    @Override
    public Validator<T> getNext() {
        return next;
    }

    @Override
    public final Violation validate(T value) throws InvalidTypeException {
        Class<?> myType = acceptType();
        Class<?> valueType = value.getClass();
        if (!myType.isAssignableFrom(valueType)){
            throw new InvalidTypeException(this.getClass().getSimpleName()+" invalid type access");
        }
        if (!this.isValid(value)){
            return violationBuilder(value);
        }
        if( next != null){
            return next.validate(value);
        }
        return null;
    }
}
