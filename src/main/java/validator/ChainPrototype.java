package validator;

import utils.exceptions.ValidatorNotFoundException;

import java.lang.annotation.Annotation;

public interface ChainPrototype<T> {
    ChainPrototype<T> cloneChain();

    ChainPrototype<T> insert(Validator<?,T> validator);

    ChainPrototype<T> append(Validator<?,T> validator);

    void validate(T value);

    Validator<?,T> find(Class<Validator<?,T>> target) throws ValidatorNotFoundException;
}
