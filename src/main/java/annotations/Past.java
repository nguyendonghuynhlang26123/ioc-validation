package annotations;

import annotations.impl.NotNullValidator;
import annotations.impl.PastValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@ValidatedBy(clazz = PastValidator.class)
public @interface Past {
    String message() default "Date is not in the past";
}
