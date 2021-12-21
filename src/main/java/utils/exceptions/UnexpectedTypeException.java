package utils.exceptions;

public class UnexpectedTypeException extends RuntimeException{
    public UnexpectedTypeException() {
    }

    public UnexpectedTypeException(String message) {
        super(message);
    }

    public UnexpectedTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
