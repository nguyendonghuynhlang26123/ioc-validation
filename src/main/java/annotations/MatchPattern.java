package annotations;

import annotations.impl.PatternValidator;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@ValidatedBy(clazz = PatternValidator.class)
public @interface MatchPattern {
    String pattern();
    
    String message() default "Data not match the pattern!";
}
