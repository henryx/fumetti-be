package com.application.fumetti.enums;

public enum Results {
    OK("ok"),
    KO("ko");

    private String result;

    Results(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
