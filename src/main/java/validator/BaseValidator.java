package validator;

import utils.exceptions.ConstraintDeclareException;
import violation.Violation;
import utils.exceptions.UnexpectedTypeException;
import violation.ViolationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

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
        this.annotationClass = (Class<Ctx>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.ctx = null;
    }

    public BaseValidator(BaseValidator<Ctx, T> other){
        this.annotationClass = other.annotationClass;
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
    public final void processValidation(T value, ViolationContext context) throws UnexpectedTypeException {
        Class<?> myType = supportType();
        Class<?> valueType = value.getClass();
        if (!myType.isAssignableFrom(valueType)){
            throw new UnexpectedTypeException(this.getClass().getSimpleName()+" invalid type access");
        }
        if (!this.isValid(value)){
            context.addViolation(getMessage(), value, annotationClass);
        }
        if( next != null){
            next.processValidation(value, context);
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
}
