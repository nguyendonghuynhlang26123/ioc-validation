package violation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ViolationBuilder {
    Violation data;

    public ViolationBuilder() {
        data = new Violation();
    }

    ViolationBuilder value(Object value) {
        this.data.setObject(value);
        return this;
    }
    ViolationBuilder message(String message) {
        this.data.setMessage(message);
        return this;
    }
    ViolationBuilder constraint( Class<? extends Annotation> constraintClass) {
        if (constraintClass != null)
            this.data.setConstraintClass(constraintClass);
        return this;
    }
    ViolationBuilder root(Object root) {
        if (root != null)
            this.data.setRootClass(root.getClass());
        return this;
    }
    ViolationBuilder field(Field field) {
        if (field != null)
            this.data.setField(field);
        return this;
    }

    Violation build(){
        return data;
    }
}
