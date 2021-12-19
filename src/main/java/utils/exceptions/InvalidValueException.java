package utils.exceptions;

/***
 * Don't use this
 */
public class InvalidValueException extends ViolationException{
    public InvalidValueException() {
    }

    public InvalidValueException(String message) {
        super(message);
    }

    public InvalidValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
