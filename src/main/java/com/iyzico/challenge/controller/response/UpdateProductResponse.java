package com.iyzico.challenge.controller.response;

import com.iyzico.challenge.dto.ProductDTO;

public class UpdateProductResponse extends BaseResponse {

    private ProductDTO product;

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
}
