package validator;

public interface Validatable {
    default void validate(){
        ValidationProvider validationProvider = ValidationProvider.getInstance();
        validationProvider.validate(this);
    }
}
