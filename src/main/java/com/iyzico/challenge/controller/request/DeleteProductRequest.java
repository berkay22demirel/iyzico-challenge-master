package com.iyzico.challenge.controller.request;

import com.iyzico.challenge.validation.Update;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;

public class DeleteProductRequest extends BaseRequest {

    @NotNull(message = "product_id.not_null", groups = Update.class)
    private Long productId;

    @NonNull
    public Long getProductId() {
        return productId;
    }

    public void setProductId(@NonNull Long productId) {
        this.productId = productId;
    }
}
