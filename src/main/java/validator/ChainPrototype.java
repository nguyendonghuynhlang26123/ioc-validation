package validator;

import utils.exceptions.ValidatorNotFoundException;

import java.lang.annotation.Annotation;

public interface ChainPrototype<T> {
    //Clone method
    ChainPrototype<T> cloneChain();

    //Chain methods
    ChainPrototype<T> insert(Validator<?,T> validator);
    ChainPrototype<T> append(Validator<?,T> validator);
    Validator<?,T> find(Class<Validator<?,T>> target) throws ValidatorNotFoundException;

    //Business logic method
    void validate(T value);

}
