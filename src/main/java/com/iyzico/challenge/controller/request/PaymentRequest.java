package com.iyzico.challenge.controller.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PaymentRequest extends BaseRequest {

    @NotNull(message = "product_id.not_null")
    private Long productId;
    @NotNull(message = "product_quantity.not_null")
    @Min(value = 1, message = "product_quantity.min")
    private Integer productQuantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
}
