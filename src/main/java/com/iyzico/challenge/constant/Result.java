package com.iyzico.challenge.constant;

public enum Result {

    FAILURE(0),
    SUCCESS(1);

    private Integer value;

    Result(Integer result) {
        this.value = result;
    }

    public Integer getValue() {
        return value;
    }
}
