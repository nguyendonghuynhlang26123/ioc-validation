package annotations;

import validator.Validator;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE) // it means this annotation should be annotated above annotation.
public @interface ValidatedBy {
    Class<? extends Validator<?,?>> clazz();
}