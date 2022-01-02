package annotations;

import annotations.impl.MaxValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@ValidatedBy(clazz = MaxValidator.class)
public @interface Max {
    long value();
    String message() default "Your value should not greater than MAX";
}
