package validator;

import violation.Violation;
import utils.exceptions.ValidatorNotFoundException;

public interface ChainPrototype<T> {
    //Clone method
    ChainPrototype<T> cloneChain();

    //Chain methods
    ChainPrototype<T> insert(Validator<T> validator);
    ChainPrototype<T> append(Validator<T> validator);
    Validator<T> find(Class<Validator<T>> target);

    //Business logic method
    Violation validate(T value);
    void validateAndThrow(T value);
}
