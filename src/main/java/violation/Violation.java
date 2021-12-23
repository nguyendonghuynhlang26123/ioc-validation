package violation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Violation {
    Object object;
    String message;
    Annotation constraint;
    Class<?> rootClass;
    String field;

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

    public Violation(Object object, String message, Annotation constraint, Class<?> rootClass, String field) {
        this.object = object;
        this.message = message;
        this.constraint = constraint;
        this.rootClass = rootClass;
        this.field = field;
    }

    @Override
    public String toString() {
        return "Violation{" +
                "value='" + object + "'" +
                ", message='" + message + "'" +
                ", constraint='" + constraint + "'" +
                ", class='" + rootClass.getSimpleName() + "'" +
                (field != null ?  ", field='" + field + "'" : "")+
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

    public void setField(String field) {
        this.field = field;
    }
}
