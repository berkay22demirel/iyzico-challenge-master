package com.iyzico.challenge.controller.request;

import com.iyzico.challenge.dto.ProductDTO;

import javax.validation.Valid;

public class AddProductRequest extends BaseRequest {
    
    @Valid
    private ProductDTO product = new ProductDTO();

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
}
