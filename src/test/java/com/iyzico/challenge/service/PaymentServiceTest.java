package com.iyzico.challenge.service;

import com.iyzico.challenge.constant.Result;
import com.iyzico.challenge.dto.PaymentDTO;
import com.iyzico.challenge.dto.ProductDTO;
import com.iyzico.challenge.repository.PaymentRepository;
import com.iyzipay.model.Cancel;
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

        when(productService.findByIdAndMerchantId(1l, 1l)).thenThrow(Exception.class);
        paymentService.pay(1L, 1L, 1, "tr");
    }

    @Test(expected = Exception.class)
    public void pay_ShouldThrowException_WhenThrowExceptionFromCheckStockService() {

        doThrow(Exception.class).when(productService).checkStock(1l, 1l, 1);
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
        when(productService.findByIdAndMerchantId(1l, 1l)).thenReturn(product);
        when(paymentRepository.save(any())).thenReturn(null);
        PaymentDTO paymentDTO = paymentService.pay(1L, 1L, 1, "tr");

        Assert.assertEquals(Result.FAILURE.getValue(), paymentDTO.getResult());
    }

    @Test
    public void pay_ShouldSavePayment_WhenReturnFailureFromIyzicoPaymentPay() {
        Payment iyzicoPaymentResult = new Payment();
        iyzicoPaymentResult.setStatus(Status.FAILURE.getValue());
        iyzicoPaymentResult.setPaymentId("paymentId");
        ProductDTO product = new ProductDTO();
        product.setPrice(BigDecimal.ONE);

        when(iyzicoPaymentProvider.pay(any(), any(), anyString(), anyString())).thenReturn(iyzicoPaymentResult);
        when(productService.findByIdAndMerchantId(1l, 1l)).thenReturn(product);
        when(paymentRepository.save(any())).thenReturn(null);
        paymentService.pay(1L, 1L, 1, "tr");

        verify(paymentRepository, times(1)).save(any());
    }

    @Test
    public void pay_ShouldUpdateStock_WhenReturnSuccessFromIyzicoPaymentPay() {
        Payment iyzicoPaymentResult = new Payment();
        iyzicoPaymentResult.setStatus(Status.SUCCESS.getValue());
        iyzicoPaymentResult.setPaymentId("paymentId");
        ProductDTO product = new ProductDTO();
        product.setPrice(BigDecimal.ONE);

        when(iyzicoPaymentProvider.pay(any(), any(), anyString(), anyString())).thenReturn(iyzicoPaymentResult);
        when(productService.findByIdAndMerchantId(1l, 1l)).thenReturn(product);
        when(paymentRepository.save(any())).thenReturn(null);
        when(productService.updateStock(anyLong(), anyLong(), anyInt())).thenReturn(true);
        paymentService.pay(1L, 1L, 1, "tr");

        verify(productService, times(1)).updateStock(anyLong(), anyLong(), anyInt());
    }

    @Test
    public void pay_ShouldCancelPayment_WhenReturnSuccessFromIyzicoPaymentPayAndNotUpdateStock() {
        Payment iyzicoPaymentResult = new Payment();
        iyzicoPaymentResult.setStatus(Status.SUCCESS.getValue());
        iyzicoPaymentResult.setPaymentId("paymentId");
        Cancel iyzicoCancelResponse = new Cancel();
        iyzicoCancelResponse.setStatus(Status.SUCCESS.getValue());
        ProductDTO product = new ProductDTO();
        product.setPrice(BigDecimal.ONE);

        when(iyzicoPaymentProvider.pay(any(), any(), anyString(), anyString())).thenReturn(iyzicoPaymentResult);
        when(iyzicoPaymentProvider.cancel(anyString(), anyString(), anyString())).thenReturn(iyzicoCancelResponse);
        when(productService.findByIdAndMerchantId(1l, 1l)).thenReturn(product);
        when(paymentRepository.save(any())).thenReturn(null);
        when(productService.updateStock(anyLong(), anyLong(), anyInt())).thenReturn(false);
        paymentService.pay(1L, 1L, 1, "tr");

        verify(iyzicoPaymentProvider, times(1)).cancel(anyString(), anyString(), anyString());
    }

    @Test
    public void pay_ShouldCancel_WhenReturnSuccessFromIyzicoPaymentPayAndNotUpdateStock() {
        Payment iyzicoPaymentResult = new Payment();
        iyzicoPaymentResult.setStatus(Status.SUCCESS.getValue());
        iyzicoPaymentResult.setPaymentId("paymentId");
        Cancel iyzicoCancelResponse = new Cancel();
        iyzicoCancelResponse.setStatus(Status.FAILURE.getValue());
        ProductDTO product = new ProductDTO();
        product.setPrice(BigDecimal.ONE);

        when(iyzicoPaymentProvider.pay(any(), any(), anyString(), anyString())).thenReturn(iyzicoPaymentResult);
        when(iyzicoPaymentProvider.cancel(anyString(), anyString(), anyString())).thenReturn(iyzicoCancelResponse);
        when(productService.findByIdAndMerchantId(1l, 1l)).thenReturn(product);
        when(paymentRepository.save(any())).thenReturn(null);
        when(productService.updateStock(anyLong(), anyLong(), anyInt())).thenReturn(false);
        paymentService.pay(1L, 1L, 1, "tr");

        verify(iyzicoPaymentProvider, times(1)).cancel(anyString(), anyString(), anyString());
    }

    @Test
    public void pay_ShouldLogCancel_WhenReturnSuccessFromIyzicoPaymentPayAndNotUpdateStock() {
        Payment iyzicoPaymentResult = new Payment();
        iyzicoPaymentResult.setStatus(Status.SUCCESS.getValue());
        iyzicoPaymentResult.setPaymentId("paymentId");
        ProductDTO product = new ProductDTO();
        product.setPrice(BigDecimal.ONE);

        when(iyzicoPaymentProvider.pay(any(), any(), anyString(), anyString())).thenReturn(iyzicoPaymentResult);
        when(iyzicoPaymentProvider.cancel(anyString(), anyString(), anyString())).thenThrow(RuntimeException.class);
        when(productService.findByIdAndMerchantId(1l, 1l)).thenReturn(product);
        when(paymentRepository.save(any())).thenReturn(null);
        when(productService.updateStock(anyLong(), anyLong(), anyInt())).thenReturn(false);
        paymentService.pay(1L, 1L, 1, "tr");

        verify(iyzicoPaymentProvider, times(1)).cancel(anyString(), anyString(), anyString());
    }

    @Test
    public void pay_ShouldUpdateStockCall4Times_WhenReturnSuccessFromIyzicoPaymentPayAndNotUpdateStock() {
        Payment iyzicoPaymentResult = new Payment();
        iyzicoPaymentResult.setStatus(Status.SUCCESS.getValue());
        iyzicoPaymentResult.setPaymentId("paymentId");
        Cancel iyzicoCancelResponse = new Cancel();
        iyzicoCancelResponse.setStatus(Status.SUCCESS.getValue());
        ProductDTO product = new ProductDTO();
        product.setPrice(BigDecimal.ONE);

        when(iyzicoPaymentProvider.pay(any(), any(), anyString(), anyString())).thenReturn(iyzicoPaymentResult);
        when(iyzicoPaymentProvider.cancel(anyString(), anyString(), anyString())).thenReturn(iyzicoCancelResponse);
        when(productService.findByIdAndMerchantId(1l, 1l)).thenReturn(product);
        when(paymentRepository.save(any())).thenReturn(null);
        when(productService.updateStock(anyLong(), anyLong(), anyInt())).thenReturn(false);
        paymentService.pay(1L, 1L, 1, "tr");

        verify(productService, times(4)).updateStock(anyLong(), anyLong(), anyInt());
    }

    @Test
    public void pay_ShouldCancelPayment_WhenThrowExceptionFromUpdateStock() {
        Payment iyzicoPaymentResult = new Payment();
        iyzicoPaymentResult.setStatus(Status.SUCCESS.getValue());
        iyzicoPaymentResult.setPaymentId("paymentId");
        Cancel iyzicoCancelResponse = new Cancel();
        iyzicoCancelResponse.setStatus(Status.SUCCESS.getValue());
        ProductDTO product = new ProductDTO();
        product.setPrice(BigDecimal.ONE);

        when(iyzicoPaymentProvider.pay(any(), any(), anyString(), anyString())).thenReturn(iyzicoPaymentResult);
        when(iyzicoPaymentProvider.cancel(anyString(), anyString(), anyString())).thenReturn(iyzicoCancelResponse);
        when(productService.findByIdAndMerchantId(1l, 1l)).thenReturn(product);
        when(paymentRepository.save(any())).thenReturn(null);
        doThrow(RuntimeException.class).when(productService).updateStock(anyLong(), anyLong(), anyInt());
        paymentService.pay(1L, 1L, 1, "tr");

        verify(iyzicoPaymentProvider, times(1)).cancel(anyString(), anyString(), anyString());
    }

    @Test
    public void pay_ShouldReturnFailure_WhenThrowExceptionFromUpdateStock() {
        Payment iyzicoPaymentResult = new Payment();
        iyzicoPaymentResult.setStatus(Status.SUCCESS.getValue());
        iyzicoPaymentResult.setPaymentId("paymentId");
        Cancel iyzicoCancelResponse = new Cancel();
        iyzicoCancelResponse.setStatus(Status.SUCCESS.getValue());
        ProductDTO product = new ProductDTO();
        product.setPrice(BigDecimal.ONE);

        when(iyzicoPaymentProvider.pay(any(), any(), anyString(), anyString())).thenReturn(iyzicoPaymentResult);
        when(iyzicoPaymentProvider.cancel(anyString(), anyString(), anyString())).thenReturn(iyzicoCancelResponse);
        when(productService.findByIdAndMerchantId(1l, 1l)).thenReturn(product);
        when(paymentRepository.save(any())).thenReturn(null);
        doThrow(RuntimeException.class).when(productService).updateStock(anyLong(), anyLong(), anyInt());
        PaymentDTO paymentDTO = paymentService.pay(1L, 1L, 1, "tr");

        Assert.assertEquals(Result.FAILURE.getValue(), paymentDTO.getResult());
    }

    @Test
    public void pay_ShouldSavePayment_WhenThrowExceptionFromUpdateStock() {
        Payment iyzicoPaymentResult = new Payment();
        iyzicoPaymentResult.setStatus(Status.SUCCESS.getValue());
        iyzicoPaymentResult.setPaymentId("paymentId");
        ProductDTO product = new ProductDTO();
        product.setPrice(BigDecimal.ONE);

        when(iyzicoPaymentProvider.pay(any(), any(), anyString(), anyString())).thenReturn(iyzicoPaymentResult);
        when(productService.findByIdAndMerchantId(1l, 1l)).thenReturn(product);
        when(paymentRepository.save(any())).thenReturn(null);
        doThrow(RuntimeException.class).when(productService).updateStock(anyLong(), anyLong(), anyInt());
        paymentService.pay(1L, 1L, 1, "tr");

        verify(paymentRepository, times(1)).save(any());
    }
}
