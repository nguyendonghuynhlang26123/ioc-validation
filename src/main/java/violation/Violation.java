package violation;

import java.lang.annotation.Annotation;

public class Violation {
    Object object;
    String message;
    Annotation constraint;
    Class<?> rootClass;

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
    }

    public Violation(Object object, String message, Annotation constraint, Class<?> rootClass) {
        this.object = object;
        this.message = message;
        this.constraint = constraint;
        this.rootClass = rootClass;
    }

    @Override
    public String toString() {
        return "Violation{" +
                "value='" + object + "'" +
                ", message='" + message + "'" +
                ", constraint='" + constraint + "'" +
                ", class='" + rootClass.getSimpleName() + "'" +
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
        message = "Field "+field+": "+message;
    }
}
