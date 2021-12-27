package validator.impl;

import utils.exceptions.NullValueException;
import validator.ChainPrototype;
import violation.ViolationContext;

import java.lang.reflect.Field;
import java.util.*;

public class CompositeValidatorChain<T> implements ChainPrototype<T> {

    /**
     * In most case store a single chain for each key
     * Store multiple chains in a key for nested object case
     * One chain for outer validate, one chain for the object itself
     * Ex: @NotNull
     *     private Student student;
     * In which Student implement Validatable
     */
    private Map<String, Collection<ChainPrototype>> chains;

    public CompositeValidatorChain(){
        chains = new HashMap<>();
    }

    public CompositeValidatorChain(Map<String, Collection<ChainPrototype>> chains){
        this.chains = chains;
    }

    @Override
    public ChainPrototype<T> addChain(String key, ChainPrototype chain){
        chains.computeIfAbsent(key, k->new LinkedList<>()).add(chain);
        return this;
    }

    @Override
    public ChainPrototype<T> cloneChain() {
        CompositeValidatorChain<T> clone = new CompositeValidatorChain<>();
        chains.forEach((k,v)-> v.forEach(chain->clone.addChain(k, chain.cloneChain())));
        return clone;
    }

    @Override
    public void processValidation(ValidateObject<T> value, ViolationContext context) {
        var fieldMap = processValue(value.getValue());
        chains.forEach((key, chainList) -> {
            if(fieldMap.containsKey(key)){
                chainList.forEach(chain->chain.processValidation(fieldMap.get(key), context));
            }
        });
    }

    @Override
    public void validateAndThrow(T value) {

    }

    private Map<String, ValidateObject> processValue(T value){
        Map<String, ValidateObject> values = new HashMap<>();
        if (value==null){
            return values;
        }
        for (Field field : value.getClass().getDeclaredFields()) {
            if (!field.trySetAccessible()) {
                continue;
            }
            try {
                values.put(field.getName(), new ValidateObject(field.getType(), field.get(value)));
            } catch (IllegalAccessException e) {
                System.out.println("Skip field "+field.getName());
            }
        }
        return values;
    }
}
