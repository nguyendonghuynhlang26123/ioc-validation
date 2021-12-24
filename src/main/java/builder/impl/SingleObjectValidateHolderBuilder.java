package builder.impl;

import annotations.impl.LengthValidator;
import annotations.impl.NotEmptyValidator;
import builder.ValidateHolderBuilder;
import handler.ViolationHandler;
import validator.ChainPrototype;
import validator.Validator;
import validator.ValidatorHolder;
import validator.impl.SingleObjectValidateHolder;

public class SingleObjectValidateHolderBuilder<T> implements ValidateHolderBuilder<T> {
    SingleObjectValidateHolder<T> validateHolder;

    public SingleObjectValidateHolderBuilder() {
        validateHolder = new SingleObjectValidateHolder<T>();
    }

    public SingleObjectValidateHolderBuilder(ViolationHandler handler) {
        validateHolder = new SingleObjectValidateHolder<>(handler);
    }

    @Override
    public ValidatorHolder<T> buildValidatable(){
        return validateHolder;
    }

    public ValidateHolderBuilder<T> setChain(ChainPrototype<T> chain){
        return this;
    }

    protected void addToChain(Validator validator){
        validateHolder.addValidator(validator);
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
