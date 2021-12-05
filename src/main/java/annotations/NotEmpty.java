package annotations;

import annotations.impl.NotEmptyValidatorImpl;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@ValidatedBy(clazz = NotEmptyValidatorImpl.class)
public @interface NotEmpty {
}
