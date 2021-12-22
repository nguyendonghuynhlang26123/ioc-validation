package validator.impl;

import validator.ChainPrototype;
import validator.Validatable;
import violation.Violation;
import handler.ViolationHandler;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValidatorContext implements Validatable {
    private ViolationHandler violationHandler;
    private Collection<Violation> violations;
    private Map<String, ChainPrototype> chains;
    private Map<String, Object> values;

    public ValidatorContext(ViolationHandler violationHandler, Map<String, ChainPrototype> chains,
                            Map<String, Object> values) {
        this.violationHandler = violationHandler;
        this.chains = chains;
        this.values = values;
        this.violations = new LinkedList<>();
    }

    public Collection<Violation> validate(){
        List<Violation> validationViolations = new LinkedList<>();
        chains.entrySet().stream().map(e->{
           return e.getValue().validate(values.get(e.getKey()));
        }).forEach(validationViolations::addAll);
        violations.addAll(validationViolations);
        violationHandler.notify(violations);
        return violations;
    }
}
