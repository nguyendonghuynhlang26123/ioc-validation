package utils.exceptions;

public class ProviderResolveException extends ValidationException {
    public ProviderResolveException(String message) {
        super(message);
    }

    public ProviderResolveException(String message, Throwable cause) {
        super(message, cause);
        cause.printStackTrace();
    }
}
