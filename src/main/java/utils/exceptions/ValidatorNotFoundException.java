package utils.exceptions;

public class ValidatorNotFoundException extends ProviderResolveException{
    public ValidatorNotFoundException(String message) {
        super(message);
    }

    public ValidatorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
