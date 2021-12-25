package violation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Violation {
    Object object;
    String message;
    Class<? extends Annotation> constraintClass;
    Class<?> rootClass;
    Field field;

    public Violation() {
    }

    public Violation(String message) {
        this.message = message;
    }

    public Violation(Object object, String message) {
        this.object = object;
        this.message = message;
        this.rootClass = object.getClass();
    }

    public Violation(Object object, String message, Class<? extends Annotation> constraint) {
        this.object = object;
        this.rootClass = object.getClass();
        this.message = message;
        this.constraintClass = constraint;
    }

    public Violation(Object object, String message, Class<? extends Annotation> constraint, Class<?> rootClass) {
        this.object = object;
        this.message = message;
        this.constraintClass = constraint;
        this.rootClass = rootClass;
    }

    @Override
    public String toString() {
        return "Violation{" +
                "value='" + object + "'" +
                ", message='" + message + "'" +
                ", constraint='" + constraintClass.getSimpleName() + "'" +
                ", class='" + rootClass.getSimpleName() + "'" +
                '}';
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Class<? extends Annotation> getConstraintClass() {
        return constraintClass;
    }

    public void setConstraintClass(Class<? extends Annotation> constraintClass) {
        this.constraintClass = constraintClass;
    }

    public Class<?> getRootClass() {
        return rootClass;
    }

    public void setRootClass(Class<?> rootClass) {
        this.rootClass = rootClass;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
