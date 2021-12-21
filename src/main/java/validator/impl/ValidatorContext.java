package validator.impl;

import validator.ChainPrototype;
import violation.Violation;
import violation.ViolationHandler;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidatorContext {
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

    public ValidatorContext(ViolationHandler violationHandler, Map<String, ChainPrototype> chains,
                            Map<String, Object> values, Collection<Violation> configViolations) {
        this.violationHandler = violationHandler;
        this.chains = chains;
        this.values = values;
        this.violations = configViolations;
    }

    public void validate(){
        var validationViolations = chains.entrySet().stream().map(e->{
           return e.getValue().validate(values.get(e.getKey()));
        }).collect(Collectors.toList());
        violations.addAll(validationViolations);
        violationHandler.notify(violations);
    }
}
