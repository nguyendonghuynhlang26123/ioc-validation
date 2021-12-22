package builder;

import annotations.impl.LengthValidator;
import annotations.impl.NotEmptyValidator;
import handler.ViolationHandler;
import validator.ChainPrototype;
import validator.Validator;
import validator.impl.ValidatorChain;

public class ValidatorBuilder<T> {
    Class<T> type;
    ChainPrototype<T> validatorChain;
    ViolationHandler handler;

    public ValidatorBuilder(Class<T> type) {
        this.type = type;
        validatorChain = new ValidatorChain<>();
        handler = new ViolationHandler();
    }

    public ValidatorBuilder(Class<T> type, ViolationHandler handler) {
        this.type = type;
        validatorChain = new ValidatorChain<>();
        this.handler = handler;
    }

    public ChainPrototype<T> build(){
        return validatorChain;
    }

    public ValidatorBuilder<T> setChain(ChainPrototype<T> chain){
        validatorChain = chain;
        return this;
    }

    protected void addToChain(Validator validator){
        validatorChain.append(validator);
    }

    // Others
    public ValidatorBuilder<T> handler(ViolationHandler handler){
        this.handler = handler;
        return this;
    }

    // ---------------------- Constraints ---------------------------------------
    public ValidatorBuilder<T> notEmpty(){
        this.addToChain(new NotEmptyValidator());
        return this;
    }

    public ValidatorBuilder<T> length(int min, int max){
        this.addToChain(new LengthValidator(min, max));
        return this;
    }
    public ValidatorBuilder<T> length(int max){
        this.addToChain(new LengthValidator(0, max));
        return this;
    }

    //Customize validator
    public ValidatorBuilder<T> addCustomValidator(Validator<T> validator){
        this.addToChain(validator);
        return this;
    }
}
