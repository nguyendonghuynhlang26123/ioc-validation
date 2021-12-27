package validator.impl;

import annotations.ValidatedBy;
import builder.ValidateHolderBuilder;
import builder.impl.BaseValidateHolderBuilder;
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
import java.lang.reflect.*;

//Singleton
public class ValidationProvider {
    private static final ValidationProvider INSTANCE = new ValidationProvider();
    private final ViolationHandler violationHandler;

    // NOTE: SINGLETON PATTERN
    private ValidationProvider() {
        violationHandler = new ViolationHandler();
    }

    public static ValidationProvider getInstance() {
        return INSTANCE;
    }

    //Read each fields of the object
    private ChainPrototype resolveObject(Class<?> objectClass) throws ValidationException {

//         Search for stored validator chain
        if (ChainRegistry.getInstance().hasChain(objectClass.getSimpleName())){
            return ChainRegistry.getInstance().getChain(objectClass.getSimpleName());
        }

        Field[] fields;
        Annotation[] annotations;

        ChainPrototype chain = new CompositeValidatorChain<>();

        fields = objectClass.getDeclaredFields();
        // For each field in class
        for (Field field : fields) {
            //Check if that field is accessible
            if (!field.trySetAccessible()) {
                System.err.println("Warning! Field " + field.getName() + " in " + objectClass.getName() + " are not accessible");
                continue;
            }

            annotations = field.getDeclaredAnnotations();

            ChainPrototype singleChain = new ValidatorChain();
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
                    throw new ValidatorDeclarationException(
                            validatorImpl.getName() + " must implement " + validatorImpl.getName() + "(){...} constructor");
                }

                //Check if this class has 2 generic type Ctx and T (i.e: class X extends BaseValidator<Annotation, String>)
                if (!hasSpecified2GenericType(validatorImpl)) {
                    throw new ValidatorDeclarationException(
                            validatorImpl.getName() + " must specify all generic types " +
                                    "(I.e: class " + validatorImpl.getName() + " extends BaseValidator<Annotation, String> {...} ");
                }

                try {
                    Validator validator = validatorImpl.getDeclaredConstructor().newInstance();

                    //Call init
                    Method initMethod = validatorImpl.getMethod("initialize", Annotation.class);
                    initMethod.setAccessible(true);
                    initMethod.invoke(validator, annotation);

                    //Add to chain
                    singleChain.appendValidator(validator);

                } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | InstantiationException e) {
                    System.err.println("Warning! Unexpected error found at " + annotation.annotationType());
                    throw new ProviderResolveException("Unexpected error found at " + annotation.annotationType(), e);
                }
            }

            try {
                chain.addChain(field.getName(), singleChain);

                // check for validatable field
                if(Validatable.class.isAssignableFrom(field.getType())){
                    var nestedChain = resolveObject(field.getType());
                    chain.addChain(field.getName(), nestedChain);
                }
            } catch (NoSuchMethodException e) {
                System.err.println("Warning! Unexpected error found at " + objectClass.getName());
                throw new ProviderResolveException("Invalid chain type usage at " + objectClass.getName(), e);
            }
        }
        //Register chain
        ChainRegistry.getInstance().register(objectClass.getSimpleName(), chain);
        return chain;
    }

    public ValidatorHolder wrapChain(Class<?> objectClass){
        var chain = resolveObject(objectClass);
        if (chain==null){
            throw new ValidationException("Cannot construct chain");
        }
        return new BaseValidatorHolder<>(chain, violationHandler);
    }

    private boolean hasParameterlessPublicConstructor(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSpecified2GenericType(Class<?> clazz) {
        return ((ParameterizedType) clazz
                .getGenericSuperclass()).getActualTypeArguments().length == 2;
    }

    public void registerViolationListener(ViolationListener listener){
        violationHandler.subscribe(listener);
    }

    public ViolationHandler getHandler(){
        return violationHandler;
    }

    public void removeViolationListener(ViolationListener listener){violationHandler.unsubscribe(listener);}

    public <T> ValidateHolderBuilder<T> createValidatorBuilder(){
        return new BaseValidateHolderBuilder<T>(violationHandler);
    }
}
