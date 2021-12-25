package violation;

import java.lang.annotation.Annotation;

public class Violation {
    Object object;
    String message;
    Class<? extends Annotation> constraintClass;
    Class<?> rootClass;

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

    public Object getValue() {
        return object;
    }

    public String getMessage() {
        return message;
    }


    public Class<?> getRootClass() {
        return rootClass;
    }

    public void setField(String field) {
        message = "Field "+field+": "+message;
    }
}
