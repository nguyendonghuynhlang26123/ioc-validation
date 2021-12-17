package validator;

import utils.exceptions.ValidatorNotFoundException;
import utils.exceptions.ViolationException;

import java.lang.annotation.Annotation;

public class ValidatorChain<T> {
    private Validator<? extends Annotation, T> head;

    public void validate(T value) throws ViolationException {
        head.validate(value);
        // TODO: or catch errors here???
    }

    public Validator<? extends Annotation, T> find(Class<Validator<? extends Annotation,T>> target) throws ValidatorNotFoundException {
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
    public ValidatorChain<T> add(Validator<?, T> add){
        if (head != null) {
            add.setNext(head);
        }
        head = add;
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
}
