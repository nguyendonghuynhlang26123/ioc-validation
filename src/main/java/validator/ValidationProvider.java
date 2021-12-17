package validator;

import annotations.ValidatedBy;
import utils.exceptions.ViolationException;
import utils.Violation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

//Singleton
public class ValidationProvider {
    private static final ValidationProvider INSTANCE = new ValidationProvider();
    // NOTE: SINGLETON PATTERN
    private ValidationProvider() {
    }

    public static ValidationProvider getInstance() {
        return INSTANCE;
    }

    //Read each fields of the object
    public <T> Collection<Violation> validate(T object) {
        Field[] fields;
        Annotation[] annotations;

        List<Violation> violationList = new LinkedList<>();

        fields = object.getClass().getDeclaredFields();
        // For each field in class
        for (Field field : fields) {
            //Check if that field is accessible
            if (!field.trySetAccessible()) {
                violationList.add(
                        new Violation(object, field, "Fails to access object's field.")
                );
                continue;
            }

            annotations = field.getDeclaredAnnotations();

            // Create empty validation chain
            ValidatorChain validatorChain = new ValidatorChain();

            // For each field's annotation
            for (Annotation annotation : annotations) {
                try {
                    //If Validated by is implemented
                    ValidatedBy validateBy = annotation.annotationType().getDeclaredAnnotation(ValidatedBy.class);
                    if (validateBy == null) {
                        // No Validator Class is assigned.
                        System.err.println("Warning! Not found validatedBy for annotation @" + annotation.annotationType());
                        continue;
                    }

                    // Retrieve the validatorImpl
                    Class<? extends Validator<?,?>> validatorImpl = validateBy.clazz();
                    Validator validator = validatorImpl.getDeclaredConstructor().newInstance();

                    //Call init
                    Method initMethod = validatorImpl.getMethod("initialize", Annotation.class);
                    initMethod.setAccessible(true);
                    initMethod.invoke(validator, annotation);

                    //Add to chain
                    validatorChain.add(validator);

                    // Call isValid() of validator implementation
//                    Method validationMethod = validatorImpl.getMethod("isValid", field.getType());
//                    validationMethod.setAccessible(true);
//                    boolean result = (boolean) validationMethod.invoke(validator, field.get(object));

                    // Validation return false
//                    if (!result) {
//                        violationList.add(
//                                new Violation(object, field, "Violation @" + annotation.annotationType().getSimpleName() + " failed")
//                        );
//                    }
                } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | InstantiationException e) {
                    System.err.println("Warning! Unexpected error found at " + annotation.annotationType());
                    e.printStackTrace();
                    violationList.add(
                            new Violation(object, field, e.getLocalizedMessage())
                    );
                }
            }

            // Start chain validating
            try {
                validatorChain.validate(field.get(object));
            } catch (IllegalAccessException | ViolationException e) {
                e.printStackTrace();
            }
        }

        return violationList;
    }
}
