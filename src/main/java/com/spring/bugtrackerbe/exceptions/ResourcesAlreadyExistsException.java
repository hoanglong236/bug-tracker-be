package com.spring.bugtrackerbe.exceptions;

public class ResourcesAlreadyExistsException extends RuntimeException {

    public ResourcesAlreadyExistsException() {
        super();
    }

    public ResourcesAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourcesAlreadyExistsException(String message) {
        super(message);
    }

    public ResourcesAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
