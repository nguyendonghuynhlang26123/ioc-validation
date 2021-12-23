package builder;

import annotations.impl.LengthValidator;
import annotations.impl.NotEmptyValidator;
import handler.ViolationHandler;
import validator.ChainPrototype;
import validator.Validatable;
import validator.Validator;
import validator.impl.SingleObjectValidatable;

public class ValidatableBuilder<T> {
    Class<T> type;
    SingleObjectValidatable<T> validatableContext;
    ViolationHandler handler;

    public ValidatableBuilder(Class<T> type) {
        this.type = type;
        handler = new ViolationHandler();
    }

    public ValidatableBuilder(Class<T> type, ViolationHandler handler) {
        this.type = type;
        this.handler = handler;
    }

    public Validatable buildValidatable(T value){
        validatableContext.setValue(value);
        return validatableContext;
    }

    public ValidatableBuilder<T> setChain(ChainPrototype<T> chain){
        return this;
    }

    protected void addToChain(Validator validator){
        validatableContext.addValidator(validator);
    }

    // Others
    public ValidatableBuilder<T> handler(ViolationHandler handler){
        this.handler = handler;
        return this;
    }

    // ---------------------- Constraints ---------------------------------------
    public ValidatableBuilder<T> notEmpty(){
        this.addToChain(new NotEmptyValidator());
        return this;
    }

    public ValidatableBuilder<T> length(int min, int max){
        this.addToChain(new LengthValidator(min, max));
        return this;
    }
    public ValidatableBuilder<T> length(int max){
        this.addToChain(new LengthValidator(0, max));
        return this;
    }

    //Customize validator
    public ValidatableBuilder<T> addCustomValidator(Validator<T> validator){
        this.addToChain(validator);
        return this;
    }
}
