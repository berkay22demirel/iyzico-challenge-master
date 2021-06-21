package com.iyzico.challenge.controller;

import com.iyzico.challenge.constant.Result;
import com.iyzico.challenge.controller.request.AddProductRequest;
import com.iyzico.challenge.controller.request.PaymentRequest;
import com.iyzico.challenge.controller.response.AddProductResponse;
import com.iyzico.challenge.controller.response.PaymentResponse;
import com.iyzico.challenge.dto.ProductDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.stream.IntStream;

public class PaymentIntegrationTest extends AbstractControllerTest {

    private Logger logger = LoggerFactory.getLogger(PaymentIntegrationTest.class);

    private static final String BASE_URL = "/payment";
    private static final String PAY_URL = BASE_URL + "/pay";
    private static final String ADD_URL = "/product/add";

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void paymentWithoutStock() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(2L);
        productDTO.setName("namee");
        productDTO.setDescription("description");
        addProductRequest.setProduct(productDTO);
        addProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(addProductRequest);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ADD_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        AddProductResponse addProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), AddProductResponse.class);

        if (Result.SUCCESS.getValue().equals(addProductResponse.getResult())) {
            PaymentRequest paymentRequest = new PaymentRequest();
            paymentRequest.setProductId(addProductResponse.getProduct().getId());
            paymentRequest.setMerchantId(1L);
            paymentRequest.setProductQuantity(1);
            String finalInputJson = super.mapToJson(paymentRequest);

            final PaymentResponse[] paymentResponses = new PaymentResponse[3];

            IntStream.range(0, 3).parallel().forEach(i -> {
                try {
                    MvcResult mvcResultPayment = mvc.perform(MockMvcRequestBuilders.post(PAY_URL)
                            .headers(new HttpHeaders())
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(finalInputJson)).andReturn();
                    paymentResponses[i] = super.mapFromJson(mvcResultPayment.getResponse().getContentAsString(), PaymentResponse.class);
                } catch (Exception exception) {
                    logger.debug("paymentWithoutStock test failed!");
                }
            });

            if (Result.SUCCESS.getValue().equals(paymentResponses[0].getResult()) && Result.SUCCESS.getValue().equals(paymentResponses[1].getResult())) {
                Assert.assertEquals(Result.SUCCESS.getValue(), paymentResponses[0].getResult());
                Assert.assertEquals(Result.SUCCESS.getValue(), paymentResponses[1].getResult());
                Assert.assertEquals(Result.FAILURE.getValue(), paymentResponses[2].getResult());
            } else if (Result.SUCCESS.getValue().equals(paymentResponses[0].getResult()) && Result.SUCCESS.getValue().equals(paymentResponses[2].getResult())) {
                Assert.assertEquals(Result.SUCCESS.getValue(), paymentResponses[0].getResult());
                Assert.assertEquals(Result.FAILURE.getValue(), paymentResponses[1].getResult());
                Assert.assertEquals(Result.SUCCESS.getValue(), paymentResponses[2].getResult());
            } else {
                Assert.assertEquals(Result.FAILURE.getValue(), paymentResponses[0].getResult());
                Assert.assertEquals(Result.SUCCESS.getValue(), paymentResponses[1].getResult());
                Assert.assertEquals(Result.SUCCESS.getValue(), paymentResponses[2].getResult());
            }

        } else {
            logger.debug("paymentWithoutStock test failed!");
        }


    }


}
