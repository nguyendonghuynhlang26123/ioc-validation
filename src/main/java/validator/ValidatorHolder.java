package validator;

import handler.ViolationHandler;
import violation.Violation;

import java.util.Collection;

public abstract class ValidatorHolder<T> {
    protected ViolationHandler handler;

    public ValidatorHolder(ViolationHandler handler){this.handler=handler;}

    abstract public Collection<Violation> validate(T value);

    public void setHandler(ViolationHandler handler){
        this.handler = handler;
    }
}
