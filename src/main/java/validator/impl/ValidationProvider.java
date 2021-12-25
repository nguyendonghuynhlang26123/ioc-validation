package validator.impl;

import annotations.ValidatedBy;
import builder.ValidateHolderBuilder;
import builder.impl.SingleObjectValidateHolderBuilder;
import handler.ViolationListener;
import utils.exceptions.ValidatorDeclarationException;
import utils.exceptions.ProviderResolveException;
import utils.exceptions.ValidationException;
import utils.exceptions.ValidatorNotFoundException;
import validator.ChainPrototype;
import validator.Validatable;
import validator.Validator;
import handler.ViolationHandler;
import validator.ValidatorHolder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

//Singleton
public class ValidationProvider {
    private static final ValidationProvider INSTANCE = new ValidationProvider();
    private final ViolationHandler violationHandler;
    private final Map<Class, ValidatorHolder> holderRegistry;

    // NOTE: SINGLETON PATTERN
    private ValidationProvider() {
        violationHandler = new ViolationHandler();
        holderRegistry = new HashMap<>();
    }

    public static ValidationProvider getInstance() {
        return INSTANCE;
    }

    //Read each fields of the object
    public <T> ValidatorHolder<T> resolveObject(T object) throws ValidationException {

        // Search for stored validator holders
        if (holderRegistry.containsKey(object.getClass())){
            return holderRegistry.get(object.getClass());
        }

        Field[] fields;
        Annotation[] annotations;

        Map<String, ChainPrototype> chains = new HashMap<>();

        fields = object.getClass().getDeclaredFields();
        // For each field in class
        for (Field field : fields) {
            //Check if that field is accessible
            if (!field.trySetAccessible()) {
                System.err.println("Warning! Field " + field.getName() + " in " + object.getClass().getName() + " are not accessible");
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
                        throw new ValidatorNotFoundException("Not found validatedBy for annotation @" + annotation.annotationType());
                    }

                    // Retrieve the @ValidatedBy Class
                    Class<? extends Validator<?>> validatorImpl = validateBy.clazz();

                    //Check if this class has "EMPTY" constructor. Since
                    if (!hasParameterlessPublicConstructor(validatorImpl)) {
                        // No Validator Class is assigned.
                        System.err.println("Warnning! @" + annotation.annotationType() + " should implement Empty constructor for Provider to resolve");
                        throw new ValidatorDeclarationException(" @" + annotation.annotationType() + " should implement Empty constructor for Provider to resolve");
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
                        throw new ProviderResolveException("Unexpected error found at " + annotation.annotationType(), e);
                    }
                }
                // Register chain
                ChainRegistry.getInstance().register(chainKey, validatorChain);
            }

            chains.put(field.getName(), validatorChain);
        }

        ValidatorHolder<T> holder = new PojoValidateHolder<T>(violationHandler, chains);
        holderRegistry.put(object.getClass(), holder);
        return holder;
    }

    private boolean hasParameterlessPublicConstructor(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                return true;
            }
        }
        return false;
    }

    public void registerViolationListener(ViolationListener listener){
        violationHandler.subscribe(listener);
    }

    public void removeViolationListener(ViolationListener listener){violationHandler.unsubscribe(listener);}

    public <T> ValidateHolderBuilder<T> createSingleValidatorBuilder(){
        return new SingleObjectValidateHolderBuilder<T>(violationHandler);
    }
}
