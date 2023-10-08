package com.spring.bugtrackerbe.exceptions;

public class ResourcesNotFoundException extends RuntimeException {

    public ResourcesNotFoundException() {
        super();
    }

    public ResourcesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourcesNotFoundException(String message) {
        super(message);
    }

    public ResourcesNotFoundException(Throwable cause) {
        super(cause);
    }
}
