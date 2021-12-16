package validator;

import utils.exceptions.ValidatorNotFoundException;

import java.lang.annotation.Annotation;

public class ValidatorChain<T> {
    private Validator<? extends Annotation, T> head;

    public boolean validate(T value){
        return head.isValid(value);
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
    public void add(Validator<?, T> add){
        if (head != null) {
            add.setNext(head);
        }
        head = add;
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
