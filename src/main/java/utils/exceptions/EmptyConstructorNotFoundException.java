package utils.exceptions;

public class EmptyConstructorNotFoundException extends ValidationException {
    public EmptyConstructorNotFoundException(String message) {
        super(message);
    }

    public EmptyConstructorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
