package utils;

import annotations.Ignore;
import annotations.ValidatedBy;
import utils.exceptions.ProviderResolveException;
import utils.exceptions.ValidationException;
import utils.exceptions.ValidatorDeclarationException;
import utils.exceptions.ValidatorNotFoundException;
import validator.ChainPrototype;
import validator.Validatable;
import validator.Validator;
import validator.impl.ChainRegistry;
import validator.impl.CollectionInternalValidatorChain;
import validator.impl.PojoValidatorChain;
import validator.impl.ValidatorChain;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * Read annotation wrapping fields in an objects and resolve them into {ChainPrototype}
 */
public class AnnotationResolver {
    /**
     * Read an object class which has fields wrapped with @Constraints & resolve it into {@link validator.ChainPrototype}
     * @param objectClass: Class of an object we want to resolve
     * @return {@link validator.ChainPrototype}
     */
    public ChainPrototype resolve(Class<?> objectClass) {
        //Check if in the registry contains this chain already => return it
        if (ChainRegistry.getInstance().hasChain(objectClass.getSimpleName())){
            return ChainRegistry.getInstance().getChain(objectClass.getSimpleName());
        }

        //Try to resolve it from scratch
        ChainPrototype chain = new PojoValidatorChain<>();
        _resolveObject(objectClass, chain);
        return chain;
    }

    //Read each fields of the object

    /**
     * An internal function do the dirty work of looping through all fields and resolve it into chain
     * @param objectClass: Class of an object we want to resolve
     * @return {@link validator.ChainPrototype}
     * @throws ValidationException
     */
    private void _resolveObject(Class<?> objectClass, ChainPrototype chain) throws ValidationException {
        Field[] fields = objectClass.getDeclaredFields();

        // Loop through all steps of field
        for (Field field : fields) {
            /// 1. Check if that field is accessible
            if (!field.trySetAccessible()) {
                System.err.println("Warning! Field " + field.getName() + " in " + objectClass.getName() + " are not accessible");
                continue;
            }

            /// 1.1: Skip resolve if this field is ignore
            if (field.isAnnotationPresent(Ignore.class)) continue;

            try {
                /// 2. Try to get a chain that wrap all the fields. I.e: @A @B @C String a; ==> AValidator => BValidator => CValidator
                ChainPrototype fieldOuterChain = getChainFromField(field);

                /// 3. Add Chain to current chain: field => SingleChain {AValidator => BValidator => CValidator}
                if(!fieldOuterChain.isEmpty()){
                    chain.addChain(new AddChainRequest(field.getName(), fieldOuterChain));
                }

                /* *
                 * 4.1' If this class is a collection<T>, resolve T class
                 * As a result: field => CollectionChain(SingleChain, InternalCollectionChain)
                 * */
                if (Collection.class.isAssignableFrom(field.getType())){
                    var collectionChildChain = new CollectionInternalValidatorChain<>();
                    ParameterizedType p = (ParameterizedType) field.getGenericType();
                    Class<?> genericT =(Class<?>) p.getActualTypeArguments()[0];


                    if (Validatable.class.isAssignableFrom(genericT)){
                        var nestedChain = resolve(genericT);
                        collectionChildChain.addChain(new AddChainRequest<>(nestedChain));
                    }

                    ChainPrototype genericTypeOuterChain = getChainFromGenericType(field);
                    if(!genericTypeOuterChain.isEmpty()){
                        collectionChildChain.addChain(new AddChainRequest<>(genericTypeOuterChain));
                    }

                    if(!collectionChildChain.isEmpty()){
                        chain.addChain(new AddChainRequest(field.getName(), collectionChildChain));
                    }
                }

                /**
                 * 4.2 Handle nested variable case
                 */
                else if(Validatable.class.isAssignableFrom(field.getType())){
                    var nestedChain = resolve(field.getType()); //recursively call to resolve
                    chain.addChain(new AddChainRequest(field.getName(), nestedChain));
                }
            } catch (NoSuchMethodException e) {
                System.err.println("Warning! Unexpected error found at " + objectClass.getName());
                throw new ProviderResolveException("Invalid chain type usage at " + objectClass.getName(), e);
            }
        }
        //Register chain
        ChainRegistry.getInstance().register(objectClass.getSimpleName(), chain);
    }

    private ChainPrototype getChainFromField(Field field) {
        Annotation[] annotations;
        annotations = field.getDeclaredAnnotations();
        return annotationToChain(annotations);
    }

    private ChainPrototype getChainFromGenericType(Field field){
        AnnotatedParameterizedType listType =
                (AnnotatedParameterizedType) field.getAnnotatedType();
        AnnotatedType annType = listType.getAnnotatedActualTypeArguments()[0];
        return annotationToChain(annType.getAnnotations());
    }

    private ChainPrototype annotationToChain(Annotation[] annotations){
        ChainPrototype singleChain = new ValidatorChain<>();

        // For each field's annotation
        for (Annotation annotation : annotations) {
            Class<? extends Validator<?>> validatorImpl = getValidatorImpl(annotation);

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
        return singleChain;
    }

    private Class<? extends Validator<?>> getValidatorImpl(Annotation annotation) {
        ValidatedBy validateBy = annotation.annotationType().getDeclaredAnnotation(ValidatedBy.class);

        //DO some sanity check:
        //check if @ValidatedBy is implemented
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
        return validatorImpl;
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
}
