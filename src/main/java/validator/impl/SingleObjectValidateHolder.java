package validator.impl;

import handler.ViolationHandler;
import validator.ChainPrototype;
import validator.Validatable;
import validator.Validator;
import validator.ValidatorHolder;
import violation.Violation;
import violation.ViolationContext;

import java.util.Collection;

public class SingleObjectValidateHolder<T> extends ValidatorHolder<T> {
    ChainPrototype<T> chainPrototype;

    public SingleObjectValidateHolder() {
        this.chainPrototype = new ValidatorChain<>();
        handler = new ViolationHandler();
    }

    public SingleObjectValidateHolder(ViolationHandler handler) {
        this.chainPrototype = new ValidatorChain<>();
        this.handler = handler;
    }

    public void addValidator(Validator<T> validator){
        this.chainPrototype.append(validator);
    }

    public void setChain(ChainPrototype<T> chainPrototype) {
        this.chainPrototype = chainPrototype;
    }

    @Override
    public Collection<Violation> validate(T value) {
        Collection<Violation> result = chainPrototype.processValidation(value, new ViolationContext().root(value));
        result.forEach(violation -> handler.notify(violation));
        return result;
    }
}
