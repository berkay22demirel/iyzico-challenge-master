package com.iyzico.challenge.controller.response;

import com.iyzico.challenge.dto.ProductDTO;

import java.math.BigDecimal;

public class PaymentResponse extends BaseResponse {

    private String orderId;
    private BigDecimal totalAmount;
    private ProductDTO product;
    private Integer productQuantity;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
}
