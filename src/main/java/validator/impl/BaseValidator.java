package validator.impl;

import utils.exceptions.ValidationException;
import violation.Violation;
import utils.exceptions.UnexpectedTypeException;
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
    public final Violation validate(T value) throws ValidationException {
        Class<?> myType = acceptType();
        Class<?> valueType = value.getClass();
        if (!myType.isAssignableFrom(valueType)){
            throw new UnexpectedTypeException(this.getClass().getSimpleName()+" invalid type access");
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
