package validator.impl;

import violation.Violation;
import utils.exceptions.UnexpectedTypeException;
import validator.Validator;

import java.lang.annotation.Annotation;
import java.util.Collection;

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
    public final void processValidation(T value, Collection<Violation> violations) throws UnexpectedTypeException {
        Class<?> myType = supportType();
        Class<?> valueType = value.getClass();
        if (!myType.isAssignableFrom(valueType)){
            throw new UnexpectedTypeException(this.getClass().getSimpleName()+" invalid type access");
        }
        if (!this.isValid(value)){
            violations.add(new Violation(value, violationMessage(value)));
        }
        if( next != null){
            next.processValidation(value, violations);
        }
    }
}
