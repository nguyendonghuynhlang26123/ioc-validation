package validator.impl;

import annotations.ValidatedBy;
import builder.ValidateHolderBuilder;
import builder.impl.BaseValidateHolderBuilder;
import handler.ViolationListener;
import utils.AnnotationResolver;
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
import java.util.Collection;

//Singleton
public class ValidationProvider {
    private static final ValidationProvider INSTANCE = new ValidationProvider();
    private final ViolationHandler violationHandler;
    private final AnnotationResolver resolver;

    // NOTE: SINGLETON PATTERN
    private ValidationProvider() {
        violationHandler = new ViolationHandler();
        resolver = new AnnotationResolver();
    }

    public static ValidationProvider getInstance() {
        return INSTANCE;
    }



    public ValidatorHolder wrapChain(Class<?> objectClass){
        var chain = resolver.resolve(objectClass);
        if (chain==null){
            throw new ValidationException("Cannot construct chain");
        }
        return new BaseValidatorHolder<>(chain, violationHandler);
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
