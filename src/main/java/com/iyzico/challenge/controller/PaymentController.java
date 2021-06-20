package com.iyzico.challenge.controller;

import com.iyzico.challenge.aop.annotation.ControllerLogging;
import com.iyzico.challenge.constant.Result;
import com.iyzico.challenge.controller.request.PaymentRequest;
import com.iyzico.challenge.controller.response.PaymentResponse;
import com.iyzico.challenge.dto.PaymentDTO;
import com.iyzico.challenge.service.PaymentService;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/payment", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Validated
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @ControllerLogging
    @PostMapping("/pay")
    public ResponseEntity<PaymentResponse> pay(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse response = new PaymentResponse();
        PaymentDTO payment = paymentService.pay(request.getMerchantId(), request.getProductId(), request.getProductQuantity(), LocaleContextHolder.getLocale().getLanguage());
        response.setProduct(payment.getProduct());
        response.setProductQuantity(payment.getProductQuantity());
        response.setOrderId(payment.getOrderId());
        response.setTotalAmount(payment.getTotalAmount());
        response.setResult(Result.SUCCESS.getValue());
        return ResponseEntity.ok(response);
    }
}
