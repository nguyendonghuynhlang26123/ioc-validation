package validator.impl;

import utils.exceptions.ValidatorNotFoundException;
import utils.exceptions.ViolationException;
import validator.ChainPrototype;
import validator.Validator;

import java.lang.annotation.Annotation;

public class ValidatorChain<T> implements ChainPrototype<T> {
    private Validator<? extends Annotation, T> head;
    private Validator<? extends Annotation, T> tail;

    @Override
    public void validate(T value) throws ViolationException {
        head.validate(value);
        // TODO: or catch errors here???
    }

    public ValidatorChain(){}

    public ValidatorChain(ValidatorChain<T> source){
        if(source.head!=null) {
            head = source.head.cloneValidator();
            var newChainTemp = head;
            var temp = source.head.getNext();
            while (temp != null) {
                var validator = temp.cloneValidator();
                newChainTemp = newChainTemp.setNext(validator);
                temp = temp.getNext();
            }
        }
    }

    @Override
    public Validator<?, T> find(Class<Validator<?,T>> target) throws ValidatorNotFoundException {
        var temp = head;
        while (temp != null){
            if(temp.getClass().equals(target)){
                return temp;
            }
            temp = temp.getNext();
        }
        throw new ValidatorNotFoundException();
    }

    // add from top
    @Override
    public ValidatorChain<T> insert(Validator<?, T> add){
        if (head != null) {
            add.setNext(head);
        } else {
            tail = add;
        }
        head = add;
        return this;
    }

    // add from bottom
    @Override
    public ValidatorChain<T> append(Validator<?, T> validator){
        if (tail != null){
            tail.setNext(validator);
        } else {
            head = validator;
        }
        tail = validator;
        return this;
    }

    // Halted
    public void addBefore(Validator<?,T> add, Class<Validator<?,T>> target){
        try{
            var targetValidator = find(target);
            add.setNext(targetValidator);
            // TODO: Get previous
        } catch (ValidatorNotFoundException e){
            // do something
        }
    }

    @Override
    public ChainPrototype cloneChain() {
        return new ValidatorChain<T>(this);
    }
}
