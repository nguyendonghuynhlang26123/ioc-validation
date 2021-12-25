package annotations;

import annotations.impl.NotEmptyValidator;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@ValidatedBy(clazz = NotEmptyValidator.class)
public @interface NotEmpty {
    String message() default "Value should not be empty!";
}
