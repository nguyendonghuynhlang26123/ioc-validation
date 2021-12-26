package builder.impl;

import annotations.impl.LengthValidator;
import annotations.impl.NotEmptyValidator;
import builder.ValidateHolderBuilder;
import handler.ViolationHandler;
import validator.ChainPrototype;
import validator.Validator;
import validator.ValidatorHolder;
import validator.impl.BaseValidatorHolder;
import validator.impl.ValidatorChain;

public class SingleObjectValidateHolderBuilder<T> implements ValidateHolderBuilder<T> {
    BaseValidatorHolder<T> validateHolder;
    ValidatorChain currentChain;

    public SingleObjectValidateHolderBuilder(ViolationHandler handler) {
        validateHolder = new BaseValidatorHolder<>(handler);
        currentChain = new ValidatorChain();
    }

    @Override
    public ValidatorHolder<T> buildValidatable(){
        validateHolder.setChain(currentChain);
        return validateHolder;
    }

    public ValidateHolderBuilder<T> setChain(ChainPrototype<T> chain){
        return this;
    }

    protected void addToChain(Validator validator){
        currentChain.append(validator);
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
        this.addToChain(new NotEmptyValidator());
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> length(int min, int max){
        this.addToChain(new LengthValidator(min, max));
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> length(int max){
        this.addToChain(new LengthValidator(0, max));
        return this;
    }

    //Customize validator
    @Override
    public ValidateHolderBuilder<T> addCustomValidator(Validator validator){
        this.addToChain(validator);
        return this;
    }
}
