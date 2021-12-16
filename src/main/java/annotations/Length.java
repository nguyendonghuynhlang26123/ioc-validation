package annotations;
import annotations.impl.LengthValidator;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@ValidatedBy(clazz = LengthValidator.class)
public @interface Length {
    int min() default 0;
    int max();
}
