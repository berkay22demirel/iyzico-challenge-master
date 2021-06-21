package com.iyzico.challenge.constant;

public enum ErrorConstant {

    UNEXPECTED_ERROR("1000", "unexpected.error"),
    VALIDATION_ERROR("1001", "validation.error"),
    PAYMENT_ERROR("1100", "payment.error"),
    PRODUCT_NOT_FOUND_ERROR("1200", "product.not_found.error"),
    NO_PRODUCT_IN_STOCK("1201", "no_product.in.stock");

    private String errorCode;
    private String errorMessageKey;

    ErrorConstant(String errorCode, String errorMessageKey) {
        this.errorCode = errorCode;
        this.errorMessageKey = errorMessageKey;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessageKey() {
        return errorMessageKey;
    }

}
