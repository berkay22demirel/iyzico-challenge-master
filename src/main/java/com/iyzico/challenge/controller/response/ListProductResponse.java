package com.iyzico.challenge.controller.response;

import com.iyzico.challenge.dto.ProductDTO;

import java.util.List;

public class ListProductResponse extends BaseResponse {

    private List<ProductDTO> productList;

    public List<ProductDTO> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDTO> productList) {
        this.productList = productList;
    }
}
