package validator;

import validator.impl.ValidateObject;
import violation.Violation;
import utils.exceptions.ValidatorNotFoundException;
import violation.ViolationContext;

import java.util.Collection;
import java.util.List;

public interface ChainPrototype<T> {
    //Clone method
    ChainPrototype<T> cloneChain();

    //Chain methods
    default ChainPrototype<T> insert(Validator<T> validator) throws NoSuchMethodException {
        throw new NoSuchMethodException("Add validator on composite chain not available");
    }

    default ChainPrototype<T> appendValidator(Validator<T> validator) throws NoSuchMethodException {
        throw new NoSuchMethodException("Add validator on composite chain not available");
    }

    default ChainPrototype<T> addChain(String key, ChainPrototype<T> chain) throws NoSuchMethodException {
        throw new NoSuchMethodException("Add chain on single chain not available");
    }
//    Validator<T> find(Class<Validator<T>> target);

    //Business logic method
    void processValidation(ValidateObject<T> value, ViolationContext context);
    void validateAndThrow(T value);
}
