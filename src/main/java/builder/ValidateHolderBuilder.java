package builder;

import handler.ViolationHandler;
import validator.Validator;
import validator.ValidatorHolder;

public interface ValidateHolderBuilder<T> {
    ValidatorHolder<T> buildValidatable();
    ValidateHolderBuilder<T> applyConstraint();
    ValidateHolderBuilder<T> applyConstraint(String key);
    ValidateHolderBuilder<T> applyComplexConstraints();
    ValidateHolderBuilder<T> applyComplexConstraints(String key);
    ValidateHolderBuilder<T> max(int max);
    ValidateHolderBuilder<T> handler(ViolationHandler handler);
    ValidateHolderBuilder<T> notEmpty();
    ValidateHolderBuilder<T> length(int min, int max);
    ValidateHolderBuilder<T> length(int max);
    ValidateHolderBuilder<T> addCustomValidator(Validator validator);
}
