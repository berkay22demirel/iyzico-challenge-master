package com.iyzico.challenge.exception;

import com.iyzico.challenge.constant.ErrorConstant;

public class BusinessException extends RuntimeException {

    private ErrorConstant error;

    public BusinessException(ErrorConstant error) {
        super(error.getErrorMessageKey());
        this.error = error;
    }

    public ErrorConstant getError() {
        return error;
    }
}
