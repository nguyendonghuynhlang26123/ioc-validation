package builder;

import annotations.impl.LengthValidator;
import annotations.impl.NotEmptyValidator;
import validator.Validator;
import validator.ValidatorChain;

public class ValidatorBuilder<T> {
    Class<T> type;
    ValidatorChain<T> validatorChain;

    public ValidatorBuilder(Class<T> type) {
        this.type = type;
        validatorChain = new ValidatorChain<T>();
    }

    public void addValidation(Validator validator){
        validatorChain.insert(validator);
    }

    public ValidatorChain<?> build(){
        return validatorChain;
    }

    public ValidatorBuilder<T> notEmpty(){
        this.addValidation(new NotEmptyValidator());
        return this;
    }

    public ValidatorBuilder<T> length(int min, int max){
        this.addValidation(new LengthValidator(min, max));
        return this;
    }
}
