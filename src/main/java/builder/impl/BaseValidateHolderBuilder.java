package builder.impl;

import annotations.impl.LengthValidator;
import annotations.impl.MaxValidator;
import annotations.impl.NotEmptyValidator;
import builder.ValidateHolderBuilder;
import handler.ViolationHandler;
import utils.exceptions.ChainBuilderException;
import validator.ChainPrototype;
import validator.Validator;
import validator.ValidatorHolder;
import validator.impl.BaseValidatorHolder;
import validator.impl.CompositeValidatorChain;
import validator.impl.ValidatorChain;

public class BaseValidateHolderBuilder<T> implements ValidateHolderBuilder<T> {
    BaseValidatorHolder<T> validateHolder;
    ChainPrototype rootChain;
    ChainPrototype currentChain;

    public BaseValidateHolderBuilder(ViolationHandler handler) {
        validateHolder = new BaseValidatorHolder<>(handler);
    }

    @Override
    public ValidateHolderBuilder<T> applyConstraint(){
        rootChain = new ValidatorChain();
        currentChain = rootChain;
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> applyConstraint(String key){
        currentChain = new ValidatorChain();
        addCurrentToCompositeRoot(key);
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> applyComplexConstraints(){
        rootChain = new CompositeValidatorChain();
        currentChain = rootChain;
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> applyComplexConstraints(String key){
        currentChain = new CompositeValidatorChain();
        addCurrentToCompositeRoot(key);
        return this;
    }

    private void addCurrentToCompositeRoot(String key){
        if (rootChain == null){
            rootChain = new CompositeValidatorChain();
        }
        try {
            rootChain.addChain(key, currentChain);
        } catch (NoSuchMethodException e) {
            throw new ChainBuilderException("Parent chain is not composite");
        }
    }

    @Override
    public ValidatorHolder<T> buildValidatable(){
        validateHolder.setChain(rootChain);
        return validateHolder;
    }

    public ValidateHolderBuilder<T> setChain(ChainPrototype<T> chain){
        return this;
    }

    protected void addValidatorToCurrent(Validator validator){
        try {
            currentChain.appendValidator(validator);
        } catch (NoSuchMethodException e) {
            throw new ChainBuilderException("Add validator not in single chain scope at "
                    + validator.getClass().getSimpleName());
        }
    }

    // Others
    @Override
    public ValidateHolderBuilder<T> handler(ViolationHandler handler){
        validateHolder.setHandler(handler);
        return this;
    }

    // ---------------------- Constraints ---------------------------------------

    @Override
    public ValidateHolderBuilder<T> notEmpty(){
        this.addValidatorToCurrent(new NotEmptyValidator());
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> length(int min, int max){
        this.addValidatorToCurrent(new LengthValidator(min, max));
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> length(int max){
        this.addValidatorToCurrent(new LengthValidator(0, max));
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> max(int max){
        this.addValidatorToCurrent(new MaxValidator(max));
        return this;
    }

    //Customize validator
    @Override
    public ValidateHolderBuilder<T> addCustomValidator(Validator validator){
        this.addValidatorToCurrent(validator);
        return this;
    }
}
