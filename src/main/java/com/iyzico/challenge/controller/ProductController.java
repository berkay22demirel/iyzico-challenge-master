package com.iyzico.challenge.controller;

import com.iyzico.challenge.aop.annotation.ControllerLogging;
import com.iyzico.challenge.constant.Result;
import com.iyzico.challenge.controller.request.AddProductRequest;
import com.iyzico.challenge.controller.request.DeleteProductRequest;
import com.iyzico.challenge.controller.request.ListProductRequest;
import com.iyzico.challenge.controller.request.UpdateProductRequest;
import com.iyzico.challenge.controller.response.AddProductResponse;
import com.iyzico.challenge.controller.response.DeleteProductResponse;
import com.iyzico.challenge.controller.response.ListProductResponse;
import com.iyzico.challenge.controller.response.UpdateProductResponse;
import com.iyzico.challenge.dto.ProductDTO;
import com.iyzico.challenge.service.ProductService;
import com.iyzico.challenge.validation.Create;
import com.iyzico.challenge.validation.Get;
import com.iyzico.challenge.validation.Update;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/product", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ControllerLogging
    @PostMapping("/add")
    public ResponseEntity<AddProductResponse> add(@Validated({Create.class}) @RequestBody AddProductRequest request) {
        AddProductResponse response = new AddProductResponse();
        ProductDTO productDTO = productService.add(request.getProduct(), request.getMerchantId());
        response.setProduct(productDTO);
        response.setResult(Result.SUCCESS.getValue());
        return ResponseEntity.ok(response);
    }

    @ControllerLogging
    @PutMapping("/update")
    public ResponseEntity<UpdateProductResponse> update(@Validated({Update.class}) @RequestBody UpdateProductRequest request) {
        UpdateProductResponse response = new UpdateProductResponse();
        ProductDTO productDTO = productService.update(request.getProduct(), request.getMerchantId());
        response.setProduct(productDTO);
        response.setResult(Result.SUCCESS.getValue());
        return ResponseEntity.ok(response);
    }

    @ControllerLogging
    @DeleteMapping("/delete")
    public ResponseEntity<DeleteProductResponse> delete(@Validated({Update.class}) @RequestBody DeleteProductRequest request) {
        DeleteProductResponse response = new DeleteProductResponse();
        productService.delete(request.getProductId(), request.getMerchantId());
        response.setResult(Result.SUCCESS.getValue());
        return ResponseEntity.ok(response);
    }

    @ControllerLogging
    @GetMapping("/list")
    public ResponseEntity<ListProductResponse> list(@Validated({Get.class}) @RequestBody ListProductRequest request) {
        ListProductResponse response = new ListProductResponse();
        List<ProductDTO> productList = productService.list(request.getMerchantId());
        response.setResult(Result.SUCCESS.getValue());
        response.setProductList(productList);
        return ResponseEntity.ok(response);
    }

}
