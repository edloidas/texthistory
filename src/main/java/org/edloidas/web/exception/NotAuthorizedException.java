package org.edloidas.web.exception;

/**
 * Exception indicates / should occurs when user
 * tries to do something, without been authorized.
 */
public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException() {
        super();
    }

    public NotAuthorizedException(String message) {
        super(message);
    }

    public NotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAuthorizedException(Throwable cause) {
        super(cause);
    }
}
