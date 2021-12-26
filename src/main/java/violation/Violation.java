package violation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Violation {
    Class<?> validateClass;
    String message;
    Class<? extends Annotation> constraintClass;
    Class<?> rootClass;
    Field field;

    public Violation(Class<?> validateClass, String message, Class<? extends Annotation> constraint) {
        this.validateClass = validateClass;
        this.rootClass = validateClass;
        this.message = message;
        this.constraintClass = constraint;
    }

    public Violation(Class<?> validateClass, String message, Class<? extends Annotation> constraint, Class<?> rootClass) {
        this.validateClass = validateClass;
        this.message = message;
        this.constraintClass = constraint;
        this.rootClass = rootClass;
    }

    @Override
    public String toString() {
        return "Violation{" +
                "class='" + validateClass.getName() + "'" +
                ", message='" + message + "'" +
                ", constraint='" + constraintClass.getSimpleName() + "'" +
                ", root='" + rootClass.getName() + "'" +
                ( field != null ? (", field='" + field.getName() + "'") : "") +
                '}';
    }

    public Class<?> getValidateClass() {
        return validateClass;
    }

    public void setValidateClass(Class<?> validateClass) {
        this.validateClass = validateClass;
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
