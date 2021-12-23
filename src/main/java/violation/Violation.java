package violation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

//TODO: Using builder to build violations
public class Violation {
    Object object;
    String message;
    Annotation constraint;
    Class<?> rootClass;
    Field field;

    public Violation(Object object, String message) {
        this.object = object;
        this.message = message;
        this.rootClass = object.getClass();
    }

    public Violation(Object object, String message, Annotation constraint) {
        this.object = object;
        this.rootClass = object.getClass();
        this.message = message;
        this.constraint = constraint;
        this.field = null;
    }

    public Violation(Object object, String message, Annotation constraint, Class<?> rootClass, Field field) {
        this.object = object;
        this.message = message;
        this.constraint = constraint;
        this.rootClass = rootClass;
        this.field = field;
    }

    @Override
    public String toString() {
        return "Violation{" +
                "value='" + object +
                "', message='" + message + '\'' +
                ", constraint=" + constraint +
                ", class=" + rootClass +
                (field != null ?  ", field=" + field.getName() : "")+
                '}';
    }

    public Object getValue() {
        return object;
    }

    public String getMessage() {
        return message;
    }

    public Annotation getConstraint() {
        return constraint;
    }

    public Class<?> getRootClass() {
        return rootClass;
    }

    public Field getField() {
        return field;
    }
}
