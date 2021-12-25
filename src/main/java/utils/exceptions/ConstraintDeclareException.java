package utils.exceptions;

public class ConstraintDeclareException extends ValidationException {
    public ConstraintDeclareException(String message) {
        super(message);
    }

    public ConstraintDeclareException(String message, Throwable cause) {
        super(message, cause);
    }
}
