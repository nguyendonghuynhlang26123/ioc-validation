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
//    ChainPrototype<T> insert(Validator<T> validator);
//    ChainPrototype<T> append(Validator<T> validator);
//    Validator<T> find(Class<Validator<T>> target);

    //Business logic method
    void processValidation(ValidateObject<T> value, ViolationContext context);
    void validateAndThrow(T value);
}
