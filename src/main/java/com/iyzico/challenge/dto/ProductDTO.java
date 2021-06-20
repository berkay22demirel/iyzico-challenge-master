package com.iyzico.challenge.dto;

import com.iyzico.challenge.validation.Create;
import com.iyzico.challenge.validation.Update;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductDTO {

    @NotNull(message = "product_id.not_null", groups = {Update.class})
    private Long id;
    @NotNull(message = "product_name.not_null", groups = {Update.class, Create.class})
    @NotEmpty(message = "product_name.not_empty", groups = {Update.class, Create.class})
    @NotBlank(message = "product_name.not_blank", groups = {Update.class, Create.class})
    @Length(min = 5, max = 50, message = "product_name.invalid.length", groups = {Update.class, Create.class})
    private String name;
    @NotNull(message = "product_description.not_null", groups = {Update.class, Create.class})
    @NotEmpty(message = "product_description.not_empty", groups = {Update.class, Create.class})
    @NotBlank(message = "product_description.not_blank", groups = {Update.class, Create.class})
    @Length(min = 5, max = 100, message = "product_description.invalid.length", groups = {Update.class, Create.class})
    private String description;
    @NotNull(message = "product_stock.not_null", groups = {Update.class, Create.class})
    @Min(value = 0, message = "product_stock.min", groups = {Update.class, Create.class})
    private Long stock;
    @NotNull(message = "product_price.not_null", groups = {Update.class, Create.class})
    @Min(value = 1, message = "product_price.min", groups = {Update.class, Create.class})
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
