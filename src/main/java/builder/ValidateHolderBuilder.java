package builder;

import handler.ViolationHandler;
import validator.Validator;
import validator.ValidatorHolder;

public interface ValidateHolderBuilder<T> {
    ValidatorHolder<T> buildValidatable();
    ValidateHolderBuilder<T> applyConstraint();
    ValidateHolderBuilder<T> applyConstraint(String field);
    ValidateHolderBuilder<T> applyPojoConstraints();
    ValidateHolderBuilder<T> applyPojoConstraints(String field);
    ValidateHolderBuilder<T> applyCollectionConstraints();
    ValidateHolderBuilder<T> applyCollectionConstraints(String field);
    ValidateHolderBuilder<T> endPojoConstraint();
    ValidateHolderBuilder<T> max(int max);
    ValidateHolderBuilder<T> handler(ViolationHandler handler);
    ValidateHolderBuilder<T> notEmpty();
    ValidateHolderBuilder<T> length(int min, int max);
    ValidateHolderBuilder<T> length(int max);
    ValidateHolderBuilder<T> addCustomValidator(Validator validator);
}
