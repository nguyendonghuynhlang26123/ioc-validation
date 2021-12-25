package validator.impl;

import utils.exceptions.NullValueException;
import validator.ChainPrototype;
import validator.ValidatorHolder;
import violation.Violation;
import handler.ViolationHandler;
import violation.ViolationContext;

import java.lang.reflect.Field;
import java.util.*;

public class PojoValidateHolder<T> extends ValidatorHolder<T> {
    private Map<String, ChainPrototype> chains; //Classname -> ViolationChain

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
        if (value == null) throw new NullValueException("Cannot validate null value");
        var fieldMap = getAllFields(value);
        List<Violation> violations = new LinkedList<>();

        chains.entrySet().stream().map(e->{
            try {
                ChainPrototype chain = e.getValue();
                String fieldName = e.getKey();
                Object fieldValue = fieldMap.get(fieldName).get(value);
                ViolationContext context = new ViolationContext().root(value).field(fieldMap.get(fieldName));

                return chain.processValidation(fieldValue, context);
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }

           return Collections.EMPTY_LIST;
        }).forEach(violations::addAll);
        violations.forEach(handler::notify);
        return violations;
    }

    private Map<String, Field> getAllFields(T value){
        Map<String, Field> values = new HashMap<>();
        for (Field field : value.getClass().getDeclaredFields()) {
            if (!field.trySetAccessible()) {
                continue;
            }
            values.put(field.getName(), field);
        }
        return values;
    }
}
