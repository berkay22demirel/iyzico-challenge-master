package com.iyzico.challenge.controller.request;

import com.iyzico.challenge.dto.ProductDTO;
import org.springframework.lang.NonNull;

import javax.validation.Valid;

public class UpdateProductRequest extends BaseRequest {

    @Valid
    private ProductDTO product;

    @NonNull
    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(@NonNull ProductDTO product) {
        this.product = product;
    }
}
