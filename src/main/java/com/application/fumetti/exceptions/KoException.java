package com.application.fumetti.exceptions;

import java.util.HashMap;

public class KoException extends Exception {
    private final HashMap details;
    private final String operation;
    private int statusCode;

    /**
     * Create a KoException
     *
     * @param statusCode Set the HTTP status code
     * @param operation  Operation executed
     * @param message    Returned message
     */
    public KoException(int statusCode, String operation, String message) {
        super(message);

        this.operation = operation;
        this.statusCode = statusCode;
        this.details = null;
    }

    /**
     * Create a KoException
     *
     * @param statusCode Set the HTTP status code
     * @param operation  Operation executed
     * @param message    Returned message
     * @param details    An hashmap with details of error
     */
    public KoException(int statusCode, String operation, String message, HashMap<String, String> details) {
        super(message);

        this.operation = operation;
        this.details = details;
        this.statusCode = statusCode;
    }

    public String getOperation() {
        return operation;
    }

    public HashMap getDetails() {
        return details;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
