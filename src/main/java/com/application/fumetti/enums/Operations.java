package com.application.fumetti.enums;

public enum Operations {
    BOOKS("albi"),
    COLLECTIONS("collane"),
    EDITORS("editori"),
    ERROR("error"),
    INDEX("index"),
    LOOKUP("lookup"),
    SERIES("serie");

    private final String operation;

    Operations(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
