package builder;

import annotations.NotEmpty;
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
        validatorChain.add(validator);
    }

    public ValidatorChain<?> build(){
        return validatorChain;
    }

    public ValidatorBuilder notEmpty(){
        this.addValidation(new NotEmptyValidator());
        return this;
    }

    public ValidatorBuilder length(int min, int max){
        this.addValidation(new LengthValidator(min, max));
        return this;
    }
}