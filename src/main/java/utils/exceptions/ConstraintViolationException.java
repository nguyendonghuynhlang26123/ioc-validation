package utils.exceptions;

/***
 * Don't use this
 */
public class ConstraintViolationException extends ValidationException {

    public ConstraintViolationException(String message) {
        super(message);
    }

    public ConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
