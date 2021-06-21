package com.iyzico.challenge.service;

import com.iyzico.challenge.constant.Result;
import com.iyzico.challenge.dto.PaymentDTO;
import com.iyzico.challenge.dto.ProductDTO;
import com.iyzico.challenge.repository.PaymentRepository;
import com.iyzipay.model.Payment;
import com.iyzipay.model.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private IyzicoPaymentProvider iyzicoPaymentProvider;
    @Mock
    private ProductService productService;
    @Mock
    private Logger logger;

    @Before
    public void setUp() {
        initMocks(this);
        doNothing().when(logger).info(anyString());
        doNothing().when(logger).error(anyString());
        doNothing().when(logger).error(anyString(), anyString(), anyString());
        doNothing().when(logger).error(anyString(), anyString(), anyString(), anyString());
    }

    @Test(expected = Exception.class)
    public void pay_ShouldThrowException_WhenThrowExceptionFromFindByIdAndMerchantIdService() {

        when(productService.findByIdAndMerchantId(1L, 1L)).thenThrow(Exception.class);
        paymentService.pay(1L, 1L, 1, "tr");
    }

    @Test(expected = Exception.class)
    public void pay_ShouldThrowException_WhenThrowExceptionFromCheckStockService() {

        doThrow(Exception.class).when(productService).checkStock(1L, 1L, 1);
        paymentService.pay(1L, 1L, 1, "tr");
    }

    @Test(expected = Exception.class)
    public void pay_ShouldThrowException_WhenThrowExceptionFromIyzicoPaymentPay() {

        doThrow(Exception.class).when(iyzicoPaymentProvider).pay(BigDecimal.ONE, new ProductDTO(), "orderId", "tr");
        paymentService.pay(1L, 1L, 1, "tr");
    }

    @Test
    public void pay_ShouldReturnFailure_WhenReturnFailureFromIyzicoPaymentPay() {
        Payment iyzicoPaymentResult = new Payment();
        iyzicoPaymentResult.setStatus(Status.FAILURE.getValue());
        iyzicoPaymentResult.setPaymentId("paymentId");
        ProductDTO product = new ProductDTO();
        product.setPrice(BigDecimal.ONE);

        when(iyzicoPaymentProvider.pay(any(), any(), anyString(), anyString())).thenReturn(iyzicoPaymentResult);
        when(productService.findByIdAndMerchantId(1L, 1L)).thenReturn(product);
        PaymentDTO paymentDTO = paymentService.pay(1L, 1L, 1, "tr");

        Assert.assertEquals(Result.FAILURE.getValue(), paymentDTO.getResult());
    }

    @Test
    public void pay_ShouldReturnSuccess_WhenReturnSuccessFromIyzicoPaymentPay() {
        Payment iyzicoPaymentResult = new Payment();
        iyzicoPaymentResult.setStatus(Status.SUCCESS.getValue());
        iyzicoPaymentResult.setPaymentId("paymentId");
        ProductDTO product = new ProductDTO();
        product.setPrice(BigDecimal.ONE);

        when(iyzicoPaymentProvider.pay(any(), any(), anyString(), anyString())).thenReturn(iyzicoPaymentResult);
        when(productService.findByIdAndMerchantId(1L, 1L)).thenReturn(product);
        PaymentDTO paymentDTO = paymentService.pay(1L, 1L, 1, "tr");

        Assert.assertEquals(Result.SUCCESS.getValue(), paymentDTO.getResult());
    }

    @Test(expected = Exception.class)
    public void save_ShouldThrowException_WhenThrowExceptionFromCheckStockService() {

        doThrow(Exception.class).when(productService).checkStock(1L, 1L, 1);
        paymentService.save(new PaymentDTO());
    }

    @Test
    public void save_ShouldInsertPayment_WhenNotThrowExceptionFromUpdateStock() {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setResult(1);
        paymentDTO.setProduct(new ProductDTO());

        doNothing().when(productService).checkStock(1L, 1L, 1);
        paymentService.save(paymentDTO);

        verify(paymentRepository, times(1)).save(any());
    }

    @Test(expected = Exception.class)
    public void cancel_ShouldThrowException_WhenThrowExceptionFromIyzicoPaymentCancel() {

        doThrow(Exception.class).when(iyzicoPaymentProvider).cancel(anyString(), anyString(), anyString());
        paymentService.cancel("orderId", "paymentId", "tr");
    }
}
