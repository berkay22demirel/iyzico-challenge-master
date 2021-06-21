package com.iyzico.challenge.controller;

import com.iyzico.challenge.constant.ErrorConstant;
import com.iyzico.challenge.constant.Result;
import com.iyzico.challenge.controller.request.PaymentRequest;
import com.iyzico.challenge.controller.response.PaymentResponse;
import com.iyzico.challenge.dto.PaymentDTO;
import com.iyzico.challenge.exception.BusinessException;
import com.iyzico.challenge.service.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class PaymentControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/payment";
    private static final String PAY_URL = BASE_URL + "/pay";

    @MockBean
    private PaymentService paymentService;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void pay_ShouldReturnSuccess_WhenThrowNotException() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setProductId(1L);
        paymentRequest.setMerchantId(1L);
        paymentRequest.setProductQuantity(1);
        String inputJson = super.mapToJson(paymentRequest);

        when(paymentService.pay(anyLong(), anyLong(), anyInt(), anyString())).thenReturn(new PaymentDTO());
        doNothing().when(paymentService).save(new PaymentDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PAY_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        PaymentResponse paymentResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), PaymentResponse.class);

        assert paymentResponse != null;
        assertEquals(Result.SUCCESS.getValue(), paymentResponse.getResult());
        assertNull(paymentResponse.getErrorCode());
        assertNull(paymentResponse.getErrorMessage());
    }

    @Test
    public void pay_ShouldReturnValidationException_WhenMerchantIdIsNull() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setProductId(1L);
        paymentRequest.setProductQuantity(1);
        String inputJson = super.mapToJson(paymentRequest);

        when(paymentService.pay(anyLong(), anyLong(), anyInt(), anyString())).thenReturn(new PaymentDTO());
        doNothing().when(paymentService).save(new PaymentDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PAY_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        PaymentResponse paymentResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), PaymentResponse.class);

        assert paymentResponse != null;
        assertEquals(Result.FAILURE.getValue(), paymentResponse.getResult());
        assertNotNull(paymentResponse.getErrorCode());
        assertNotNull(paymentResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), paymentResponse.getErrorCode());
    }

    @Test
    public void pay_ShouldReturnValidationException_WhenProductIdIsNull() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setMerchantId(1L);
        paymentRequest.setProductQuantity(1);
        String inputJson = super.mapToJson(paymentRequest);

        when(paymentService.pay(anyLong(), anyLong(), anyInt(), anyString())).thenReturn(new PaymentDTO());
        doNothing().when(paymentService).save(new PaymentDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PAY_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        PaymentResponse paymentResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), PaymentResponse.class);

        assert paymentResponse != null;
        assertEquals(Result.FAILURE.getValue(), paymentResponse.getResult());
        assertNotNull(paymentResponse.getErrorCode());
        assertNotNull(paymentResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), paymentResponse.getErrorCode());
    }

    @Test
    public void pay_ShouldReturnValidationException_WhenProductQuantityIsNull() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setMerchantId(1L);
        paymentRequest.setProductId(1L);
        String inputJson = super.mapToJson(paymentRequest);

        when(paymentService.pay(anyLong(), anyLong(), anyInt(), anyString())).thenReturn(new PaymentDTO());
        doNothing().when(paymentService).save(new PaymentDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PAY_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        PaymentResponse paymentResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), PaymentResponse.class);

        assert paymentResponse != null;
        assertEquals(Result.FAILURE.getValue(), paymentResponse.getResult());
        assertNotNull(paymentResponse.getErrorCode());
        assertNotNull(paymentResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), paymentResponse.getErrorCode());
    }

    @Test
    public void pay_ShouldReturnValidationException_WhenProductQuantityIsLessThan1() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setMerchantId(1L);
        paymentRequest.setProductId(1L);
        paymentRequest.setProductQuantity(0);
        String inputJson = super.mapToJson(paymentRequest);

        when(paymentService.pay(anyLong(), anyLong(), anyInt(), anyString())).thenReturn(new PaymentDTO());
        doNothing().when(paymentService).save(new PaymentDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PAY_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        PaymentResponse paymentResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), PaymentResponse.class);

        assert paymentResponse != null;
        assertEquals(Result.FAILURE.getValue(), paymentResponse.getResult());
        assertNotNull(paymentResponse.getErrorCode());
        assertNotNull(paymentResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), paymentResponse.getErrorCode());
    }

    @Test
    public void pay_ShouldReturnUnexpectedError_WhenThrowException() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setMerchantId(1L);
        paymentRequest.setProductId(1L);
        paymentRequest.setProductQuantity(1);
        String inputJson = super.mapToJson(paymentRequest);

        when(paymentService.pay(anyLong(), anyLong(), anyInt(), anyString())).thenThrow(RuntimeException.class);
        doNothing().when(paymentService).save(new PaymentDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PAY_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        PaymentResponse paymentResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), PaymentResponse.class);

        assert paymentResponse != null;
        assertEquals(Result.FAILURE.getValue(), paymentResponse.getResult());
        assertNotNull(paymentResponse.getErrorCode());
        assertNotNull(paymentResponse.getErrorMessage());
        assertEquals(ErrorConstant.UNEXPECTED_ERROR.getErrorCode(), paymentResponse.getErrorCode());
    }

    @Test
    public void pay_ShouldReturnErrorThatThrowBusinessError_WhenThrowBusinessException() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setMerchantId(1L);
        paymentRequest.setProductId(1L);
        paymentRequest.setProductQuantity(1);
        String inputJson = super.mapToJson(paymentRequest);

        when(paymentService.pay(anyLong(), anyLong(), anyInt(), anyString())).thenThrow(new BusinessException(ErrorConstant.PRODUCT_NOT_FOUND_ERROR));
        doNothing().when(paymentService).save(new PaymentDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PAY_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        PaymentResponse paymentResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), PaymentResponse.class);

        assert paymentResponse != null;
        assertEquals(Result.FAILURE.getValue(), paymentResponse.getResult());
        assertNotNull(paymentResponse.getErrorCode());
        assertNotNull(paymentResponse.getErrorMessage());
        assertEquals(ErrorConstant.PRODUCT_NOT_FOUND_ERROR.getErrorCode(), paymentResponse.getErrorCode());
    }

    @Test
    public void pay_ShouldReturnNoProductInStockError_WhenThrowObjectOptimisticLockingFailureException() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setMerchantId(1L);
        paymentRequest.setProductId(1L);
        paymentRequest.setProductQuantity(1);
        String inputJson = super.mapToJson(paymentRequest);
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setResult(Result.SUCCESS.getValue());

        when(paymentService.pay(anyLong(), anyLong(), anyInt(), anyString())).thenReturn(paymentDTO);
        doThrow(ObjectOptimisticLockingFailureException.class).when(paymentService).save(paymentDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PAY_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        PaymentResponse paymentResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), PaymentResponse.class);

        assert paymentResponse != null;
        assertEquals(Result.FAILURE.getValue(), paymentResponse.getResult());
        assertNotNull(paymentResponse.getErrorCode());
        assertNotNull(paymentResponse.getErrorMessage());
        assertEquals(ErrorConstant.NO_PRODUCT_IN_STOCK.getErrorCode(), paymentResponse.getErrorCode());
    }

    @Test
    public void pay_ShouldReturnPaymentError_WhenThrowObjectOptimisticLockingFailureException() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setMerchantId(1L);
        paymentRequest.setProductId(1L);
        paymentRequest.setProductQuantity(1);
        String inputJson = super.mapToJson(paymentRequest);
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setResult(Result.SUCCESS.getValue());

        when(paymentService.pay(anyLong(), anyLong(), anyInt(), anyString())).thenReturn(paymentDTO);
        doThrow(RuntimeException.class).when(paymentService).save(paymentDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PAY_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        PaymentResponse paymentResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), PaymentResponse.class);

        assert paymentResponse != null;
        assertEquals(Result.FAILURE.getValue(), paymentResponse.getResult());
        assertNotNull(paymentResponse.getErrorCode());
        assertNotNull(paymentResponse.getErrorMessage());
        assertEquals(ErrorConstant.PAYMENT_ERROR.getErrorCode(), paymentResponse.getErrorCode());
    }

    @Test
    public void pay_ShouldCancelPayment_WhenThrowException() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setMerchantId(1L);
        paymentRequest.setProductId(1L);
        paymentRequest.setProductQuantity(1);
        String inputJson = super.mapToJson(paymentRequest);
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setResult(Result.SUCCESS.getValue());
        paymentDTO.setPaymentId("paymentId");
        paymentDTO.setOrderId("orderId");

        when(paymentService.pay(anyLong(), anyLong(), anyInt(), anyString())).thenReturn(paymentDTO);
        doThrow(RuntimeException.class).when(paymentService).save(paymentDTO);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(PAY_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        PaymentResponse paymentResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), PaymentResponse.class);

        verify(paymentService, times(1)).cancel(anyString(), anyString(), anyString());
    }
}
