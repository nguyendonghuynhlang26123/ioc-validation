package violation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class ViolationContext {
    Object rootObject;
    Field field;
    Collection<Violation> violations;

    public ViolationContext() {
        violations = new ArrayList<>();
    }

    public ViolationContext root(Object root){
        this.rootObject = root;
        return this;
    }

    public ViolationContext field(Field field){
        this.field = field;
        return this;
    }

    public Collection<Violation> getViolations() {
        return violations;
    }

    public ViolationContext addViolation(String message, Object validatingValue, Class<? extends Annotation> constraintClass){
        ViolationBuilder builder = new ViolationBuilder();
        violations.add(builder
                .root(rootObject)
                .field(field)
                .constraint(constraintClass)
                .message(message)
                .value(validatingValue)
                .build());
        return this;
    }
}
