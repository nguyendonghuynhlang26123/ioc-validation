package utils;

import java.lang.reflect.Field;

//TODO: Using builder to build violations
public class Violation {
    Object object;
    Field field;
    String reason;

    public Violation(Object object, Field field, String reason) {
        this.object = object;
        this.field = field;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Violation{" +
                "object=" + object.getClass().getSimpleName() +
                ", field=" + field.getName() +
                ", reason='" + reason + '\'' +
                '}';
    }
}
