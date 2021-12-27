package utils.exceptions;

public class ChainBuilderException extends RuntimeException{
    public ChainBuilderException(String message) {
        super(message);
    }

    public ChainBuilderException(String message, Throwable cause) {
        super(message, cause);
    }
}
