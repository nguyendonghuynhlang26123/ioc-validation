package utils.exceptions;

public class ValidatorDeclarationException extends ValidationException {
    public ValidatorDeclarationException(String message) {
        super(message);
    }

    public ValidatorDeclarationException(String message, Throwable cause) {
        super(message, cause);
    }
}
