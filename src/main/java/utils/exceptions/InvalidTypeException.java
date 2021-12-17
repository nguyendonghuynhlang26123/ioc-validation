package utils.exceptions;

public class InvalidTypeException extends ViolationException{
    public InvalidTypeException() {
    }

    public InvalidTypeException(String message) {
        super(message);
    }

    public InvalidTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
