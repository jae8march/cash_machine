package com.company.app.util.exception;

/**
 * Project exceptions.
 */
public class ProjectException extends Exception {
    public ProjectException() {
    }

    public ProjectException(String message) {
        super(message);
    }

    public ProjectException(String message, Throwable cause) {
        super(message, cause);
    }
}
