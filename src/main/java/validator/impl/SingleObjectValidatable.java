package validator.impl;

import validator.ChainPrototype;
import validator.Validatable;
import validator.Validator;
import violation.Violation;

import javax.xml.validation.ValidatorHandler;
import java.util.Collection;

public class SingleObjectValidatable<T> implements Validatable {
    ChainPrototype<T> chainPrototype;
    ValidatorHandler handler;
    T value;

    public SingleObjectValidatable(T value) {
        this.chainPrototype = new ValidatorChain<>();
        this.value = value;
    }

    public SingleObjectValidatable(T value, ValidatorHandler handler) {
        this.handler = handler;
        this.value = value;
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

    @Override
    public Collection<Violation> validate() {
        return chainPrototype.validate(value);
    }
}
