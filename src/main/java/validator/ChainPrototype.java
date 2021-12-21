package validator;

import violation.Violation;
import utils.exceptions.ValidatorNotFoundException;

import java.util.Collection;
import java.util.List;

public interface ChainPrototype<T> {
    //Clone method
    ChainPrototype<T> cloneChain();

    //Chain methods
    ChainPrototype<T> insert(Validator<T> validator);
    ChainPrototype<T> append(Validator<T> validator);
    Validator<T> find(Class<Validator<T>> target);

    //Business logic method
    Collection<Violation> validate(T value);
    void validateAndThrow(T value);
}
