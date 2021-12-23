package validator.impl;

import validator.ChainPrototype;
import validator.Validatable;
import violation.Violation;
import handler.ViolationHandler;

import java.util.*;

public class PojoObjectValidatable implements Validatable {
    private ViolationHandler violationHandler;
    private Map<String, ChainPrototype> chains;
    private Map<String, Object> values;

    public PojoObjectValidatable(ViolationHandler violationHandler, Map<String, ChainPrototype> chains,
                                 Map<String, Object> values) {
        this.violationHandler = violationHandler;
        this.chains = chains;
        this.values = values;
    }

    public Collection<Violation> validate(){
        List<Violation> violations = new LinkedList<>();
        chains.entrySet().stream().map(e->{
           return e.getValue().processValidation(values.get(e.getKey()));
        }).forEach(violations::addAll);
        violations.forEach(violation -> violationHandler.notify(violation) );
        return violations;
    }
}
