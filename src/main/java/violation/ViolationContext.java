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
        Violation violation = new Violation(validatingValue, message, constraintClass);
        if (rootObject != null) violation.setRootClass(rootObject.getClass());
        if (field != null) violation.setField(field);
        violations.add(violation);
        return this;
    }
}
