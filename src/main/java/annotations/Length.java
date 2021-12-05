package annotations;
import annotations.impl.LengthValidatorImpl;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@ValidatedBy(clazz = LengthValidatorImpl.class)
public @interface Length {
    int min() default 0;
    int max();
}
