package validator.impl;

import handler.ViolationHandler;
import validator.ChainPrototype;
import validator.Validatable;
import validator.Validator;
import violation.Violation;

import java.util.Collection;

public class SingleObjectValidatable<T> implements Validatable {
    ChainPrototype<T> chainPrototype;
    ViolationHandler handler;
    T value;

    public SingleObjectValidatable() {
        this.chainPrototype = new ValidatorChain<>();
        handler = new ViolationHandler();
    }

    public SingleObjectValidatable(ViolationHandler handler) {
        this.chainPrototype = new ValidatorChain<>();
        this.handler = handler;
    }

    public void addValidator(Validator<T> validator){
        this.chainPrototype.append(validator);
    }

    public void setChain(ChainPrototype<T> chainPrototype) {
        this.chainPrototype = chainPrototype;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setHandler(ViolationHandler handler) {
        this.handler = handler;
    }

    @Override
    public Collection<Violation> validate() {
        Collection<Violation> result = chainPrototype.processValidation(value);
        result.forEach(violation -> handler.notify(violation));
        return result;
    }
}
