package utils.exceptions;

import utils.Violation;

import java.lang.reflect.Field;

/***
 * Don't use this
 */
public class ViolationException extends RuntimeException{
    public ViolationException() {
    }

    public ViolationException(String message) {
        super(message);
    }

    public ViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
