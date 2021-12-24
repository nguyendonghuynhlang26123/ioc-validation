package validator.impl;

import validator.ChainPrototype;
import validator.Validatable;
import validator.ValidatorHolder;
import violation.Violation;
import handler.ViolationHandler;

import java.lang.reflect.Field;
import java.util.*;

public class PojoValidateHolder<T> extends ValidatorHolder<T> {

    private Map<String, ChainPrototype> chains;

    public PojoValidateHolder(){
        chains = new HashMap<>();
    }

    public PojoValidateHolder(ViolationHandler violationHandler){
        this.handler = violationHandler;
    }

    public PojoValidateHolder(ViolationHandler violationHandler, Map<String, ChainPrototype> chains) {
        this.handler = violationHandler;
        this.chains = chains;
    }

    @Override
    public Collection<Violation> validate(T value){
        var values = processValue(value);
        List<Violation> violations = new LinkedList<>();
        chains.entrySet().stream().map(e->{
           Collection<Violation> result = e.getValue().processValidation(values.get(e.getKey()));
           result.forEach(violation -> violation.setField(e.getKey()));
           return result;
        }).forEach(violations::addAll);
        violations.forEach(handler::notify);
        return violations;
    }

    private Map<String, Object> processValue(T value){
        Map<String, Object> values = new HashMap<>();
        for (Field field : value.getClass().getDeclaredFields()) {
            if (!field.trySetAccessible()) {
                continue;
            }
            try{
                values.put(field.getName(), field.get(value));
            } catch (IllegalAccessException e){
                System.out.println("Illegal access in pojo holder");
            }
        }
        return values;
    }
}
