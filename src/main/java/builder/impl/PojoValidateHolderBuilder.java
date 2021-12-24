package builder.impl;

import builder.ValidateHolderBuilder;
import handler.ViolationHandler;
import validator.Validator;
import validator.ValidatorHolder;
import validator.impl.PojoValidateHolder;

/***
 * Remove
 * @param <T>
 */
public class PojoValidateHolderBuilder<T> implements ValidateHolderBuilder<T> {
    PojoValidateHolder<T> validateHolder;

    public PojoValidateHolderBuilder() {
        validateHolder = new PojoValidateHolder<T>();
    }

    public PojoValidateHolderBuilder(ViolationHandler handler) {
        validateHolder = new PojoValidateHolder<T>(handler);
    }

    @Override
    public ValidatorHolder<T> buildValidatable() {
        return null;
    }

    @Override
    public ValidateHolderBuilder<T> handler(ViolationHandler handler) {
        return null;
    }

    @Override
    public ValidateHolderBuilder<T> notEmpty() {
        return null;
    }

    @Override
    public ValidateHolderBuilder<T> length(int min, int max) {
        return null;
    }

    @Override
    public ValidateHolderBuilder<T> length(int max) {
        return null;
    }

    @Override
    public ValidateHolderBuilder<T> addCustomValidator(Validator validator) {
        return null;
    }
}
