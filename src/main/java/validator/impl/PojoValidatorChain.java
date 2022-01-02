package validator.impl;

import utils.AddChainRequest;
import utils.ValidateObject;
import utils.exceptions.ChainBuilderException;
import validator.ChainPrototype;
import violation.ViolationContext;

import java.lang.reflect.Field;
import java.util.*;

public class PojoValidatorChain<T> implements ChainPrototype<T> {

    /**
     * In most case store a single chain for each key
     * Store multiple chains (CollectionValidatorChain) in a key for nested object case
     * One chain for outer validate, one chain for the object itself
     * Ex: @NotNull
     *     private Student student;
     * In which Student implement Validatable
     */
    protected Map<String, ChainPrototype> chains;

    public PojoValidatorChain(){
        chains = new HashMap<>();
    }

    public PojoValidatorChain(Map<String, ChainPrototype> chains){
        this.chains = chains;
    }

    @Override
    public ChainPrototype<T> addChain(AddChainRequest<T> chainRequest){
        if (chainRequest.getField()==null){
            throw new ChainBuilderException("Add chain to composite with no field name key");
        }
//        chains.computeIfAbsent(chainRequest.getField(), k->new LinkedList<>()).add(chainRequest.getChain());
        String field = chainRequest.getField();
        if (chains.containsKey(field)){
            var currentChain = chains.get(field);
            try {
                currentChain.addChain(chainRequest);
            } catch (NoSuchMethodException| ChainBuilderException exception){
                CollectionValidatorChain collectionChain = new CollectionValidatorChain<>();
                collectionChain.addChain(new AddChainRequest(currentChain));
                collectionChain.addChain(chainRequest);
                chains.put(field, collectionChain);
            }
        } else {
            chains.put(field, chainRequest.getChain());
        }
        return this;
    }

    @Override
    public boolean isEmpty() {
        return chains.isEmpty();
    }

    @Override
    public ChainPrototype<T> cloneChain() {
        PojoValidatorChain<T> clone = new PojoValidatorChain<>();
//        chains.forEach((k,v)-> v.forEach(chain->clone.implicitAddChain(k, chain.cloneChain())));
        chains.forEach((k,v)->clone.implicitAddChain(k, v.cloneChain()));
        return clone;
    }

    private void implicitAddChain(String field, ChainPrototype chain){
//        chains.computeIfAbsent(field, k->new LinkedList<>()).add(chain);
        chains.put(field, chain);
    }

    @Override
    public void processValidation(ValidateObject<T> value, ViolationContext context) {
        var fieldMap = processValue(value.getValue());
        chains.forEach((key, chain) -> {
            if(fieldMap.containsKey(key)){
//                chainList.forEach(chain->chain.processValidation(fieldMap.get(key), context));
                chain.processValidation(fieldMap.get(key), context);
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
