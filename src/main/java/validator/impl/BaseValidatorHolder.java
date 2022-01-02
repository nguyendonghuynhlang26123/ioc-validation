package validator.impl;

import handler.ViolationHandler;
import utils.ValidateObject;
import validator.ChainPrototype;
import validator.ValidatorHolder;
import violation.Violation;
import violation.ViolationContext;

import java.util.Collection;

public class BaseValidatorHolder<T> extends ValidatorHolder<T> {
    private ChainPrototype<T> chain;

    public void setChain(ChainPrototype<T> chain) {
        this.chain = chain;
    }

    public BaseValidatorHolder(ChainPrototype<T> chain, ViolationHandler handler){
        super(handler);
        this.chain = chain;
    }

    public BaseValidatorHolder(ViolationHandler handler){
        super(handler);
        this.chain = new PojoValidatorChain<>();
    }

    @Override
    public Collection<Violation> validate(T value) {
        ValidateObject<T> validateObject;
        if (value == null) {
            validateObject = new ValidateObject(Object.class, null);
        } else {
            validateObject = new ValidateObject(value.getClass(), value);
        }
        ViolationContext context = new ViolationContext().root(value);
        chain.processValidation(validateObject, context);
        context.getViolations().forEach(handler::notify);
        return context.getViolations();
    }
}
