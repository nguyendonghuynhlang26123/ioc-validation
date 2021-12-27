package validator.impl;

import utils.exceptions.ConstraintDeclareException;
import validator.Validator;
import utils.exceptions.UnexpectedTypeException;
import violation.ViolationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public abstract class BaseValidator<Ctx extends Annotation,T> implements Validator<T> {
    private Validator<T> next;
    private Class<Ctx> annotationClass;
    private Ctx ctx;

    abstract public void onInit(Ctx ctx);

    public void initialize(Ctx ctx) {
        this.ctx = ctx;
        onInit(ctx);
    }

    public BaseValidator(){
        //can only used in CLASS implements / extends something that has generic declarations
        this.annotationClass = (Class<Ctx>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.ctx = null;
    }

    public BaseValidator(BaseValidator<Ctx, T> other){
        this.annotationClass = other.annotationClass;
        this.ctx = other.ctx;
    }

    @Override
    public Validator<T> setNext(Validator<T> validator) {
        next = validator;
        return next;
    }

    @Override
    public Validator<T> getNext() {
        return next;
    }

    @Override
    public final void processValidation(ValidateObject<T> object, ViolationContext context) throws UnexpectedTypeException {
        if (!isValidType(object.getType())){
            throw new UnexpectedTypeException(this.getClass().getSimpleName()+" invalid type access");
        }
        if (!this.isValid(object.getValue())){
            context.addViolation(getMessage(), object.getType(), annotationClass);
        }
        if( next != null){
            next.processValidation(object, context);
        }
    }

    public String getMessage(){
        try {
            Method method = annotationClass.getDeclaredMethod("message");
            method.setAccessible(true);
            if (ctx != null)
                return (String) method.invoke(ctx);
            return (String) method.getDefaultValue();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new ConstraintDeclareException("Not found message() for "
                    + annotationClass.getName()
                    + "! Please make sure to set declare and set default value for message() function");
        } catch(IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
            throw new ConstraintDeclareException("Failed to execute message() fn! Please make sure you set default value for it");
        }
    }

    @Override
    public boolean isValidType(Class<T> clazz) {
        //can only used in CLASS implements / extends something that has generic declarations
        Class<?> myType = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];

        return myType.isAssignableFrom(clazz);
    }
}
