package demo.customized;

import annotations.ValidatedBy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@ValidatedBy(clazz = ArrayNotEmptyValidator.class)
public @interface ArrayNotEmpty {
    String message() default "Array should not be empty";
}
