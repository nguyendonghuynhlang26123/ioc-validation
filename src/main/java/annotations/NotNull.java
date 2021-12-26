package annotations;

import annotations.impl.NotNullValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@ValidatedBy(clazz = NotNullValidator.class)
public @interface NotNull {
    String message() default "Value should not be null!";
}
