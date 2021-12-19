package validator.impl;

import annotations.ValidatedBy;
import utils.exceptions.InvalidTypeException;
import utils.exceptions.ViolationException;
import utils.Violation;
import validator.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
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
                        new Violation(object,"Fails to access object's field "+field.getName())
                );
                continue;
            }

            annotations = field.getDeclaredAnnotations();

            // Get chain from store
            String chainKey = object.getClass().getSimpleName()+"."+field.getName();
            var validatorChain = ChainRegistry.getInstance().getChain(chainKey);

            // No prototype found
            if (validatorChain == null){
                // Create new chain
                validatorChain = new ValidatorChain();
                // For each field's annotation
                for (Annotation annotation : annotations) {
                    //DO some sanity check:

                    //check if @ValidatedBy is implemented
                    ValidatedBy validateBy = annotation.annotationType().getDeclaredAnnotation(ValidatedBy.class);
                    if (validateBy == null) {
                        // No Validator Class is assigned.
                        System.err.println("Warning! Not found validatedBy for annotation @" + annotation.annotationType());
                        continue;
                    }

                    // Retrieve the @ValidatedBy Class
                    Class<? extends Validator<?>> validatorImpl = validateBy.clazz();

                    //Check if this class has "EMPTY" constructor. Since
                    if (!hasParameterlessPublicConstructor(validatorImpl)) {
                        // No Validator Class is assigned.
                        System.err.println("Warnning! @" + annotation.annotationType() + " should implement Empty constructor for Provider to resolve");
                        continue;
                    }
                    try {

                        Validator validator = validatorImpl.getDeclaredConstructor().newInstance();

                        //Call init
                        Method initMethod = validatorImpl.getMethod("initialize", Annotation.class);
                        initMethod.setAccessible(true);
                        initMethod.invoke(validator, annotation);

                        //Add to chain
                        validatorChain.append(validator);

                    } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | InstantiationException e) {
                        System.err.println("Warning! Unexpected error found at " + annotation.annotationType());
                        e.printStackTrace();
                        violationList.add(
                                new Violation(object, e.getLocalizedMessage() + "at" + field.getName())
                        );
                    }
                }
                // Register chain
                ChainRegistry.getInstance().register(chainKey, validatorChain);
            }

            // Start chain validating
            try {
                var violation = validatorChain.validate(field.get(object));
                if (violation != null){
                    violationList.add(violation);
                }
            } catch (IllegalAccessException | InvalidTypeException e) {
                e.printStackTrace();
            }
        }

        return violationList;
    }

    private boolean hasParameterlessPublicConstructor(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                return true;
            }
        }
        return false;
    }
}
