package com.iyzico.challenge.controller;

import com.iyzico.challenge.constant.ErrorConstant;
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
import com.iyzico.challenge.util.StringUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class ProductControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/product";
    private static final String ADD_URL = BASE_URL + "/add";
    private static final String UPDATE_URL = BASE_URL + "/update";
    private static final String DELETE_URL = BASE_URL + "/delete";
    private static final String LIST_URL = BASE_URL + "/list";

    @MockBean
    private ProductService productService;

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void add_ShouldReturnSuccess_WhenThrowNotException() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("namee");
        productDTO.setDescription("description");
        addProductRequest.setProduct(productDTO);
        addProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(addProductRequest);

        when(productService.add(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ADD_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        AddProductResponse addProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), AddProductResponse.class);

        assert addProductResponse != null;
        assertEquals(Result.SUCCESS.getValue(), addProductResponse.getResult());
        assertNull(addProductResponse.getErrorCode());
        assertNull(addProductResponse.getErrorMessage());
    }

    @Test
    public void add_ShouldReturnValidationException_WhenMerchantIdIsNull() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("namee");
        productDTO.setDescription("description");
        addProductRequest.setProduct(productDTO);
        String inputJson = super.mapToJson(addProductRequest);

        when(productService.add(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ADD_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        AddProductResponse addProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), AddProductResponse.class);

        assert addProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), addProductResponse.getResult());
        assertNotNull(addProductResponse.getErrorCode());
        assertNotNull(addProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), addProductResponse.getErrorCode());
    }

    @Test
    public void add_ShouldReturnValidationException_WhenProductNameIsNull() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setDescription("description");
        addProductRequest.setProduct(productDTO);
        addProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(addProductRequest);

        when(productService.add(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ADD_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        AddProductResponse addProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), AddProductResponse.class);

        assert addProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), addProductResponse.getResult());
        assertNotNull(addProductResponse.getErrorCode());
        assertNotNull(addProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), addProductResponse.getErrorCode());
    }

    @Test
    public void add_ShouldReturnValidationException_WhenProductNameIsEmpty() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName(StringUtil.EMPTY_STRING);
        productDTO.setDescription("description");
        addProductRequest.setProduct(productDTO);
        addProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(addProductRequest);

        when(productService.add(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ADD_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        AddProductResponse addProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), AddProductResponse.class);

        assert addProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), addProductResponse.getResult());
        assertNotNull(addProductResponse.getErrorCode());
        assertNotNull(addProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), addProductResponse.getErrorCode());
    }

    @Test
    public void add_ShouldReturnValidationException_WhenProductNameIsBlankString() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName(StringUtil.EMPTY_STRING);
        productDTO.setDescription("description");
        addProductRequest.setProduct(productDTO);
        addProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(addProductRequest);

        when(productService.add(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ADD_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        AddProductResponse addProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), AddProductResponse.class);

        assert addProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), addProductResponse.getResult());
        assertNotNull(addProductResponse.getErrorCode());
        assertNotNull(addProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), addProductResponse.getErrorCode());
    }

    @Test
    public void add_ShouldReturnValidationException_WhenProductNameLengthLessThanMin() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("n");
        productDTO.setDescription("description");
        addProductRequest.setProduct(productDTO);
        addProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(addProductRequest);

        when(productService.add(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ADD_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        AddProductResponse addProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), AddProductResponse.class);

        assert addProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), addProductResponse.getResult());
        assertNotNull(addProductResponse.getErrorCode());
        assertNotNull(addProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), addProductResponse.getErrorCode());
    }

    @Test
    public void add_ShouldReturnValidationException_WhenProductNameLengthGreaterThanMax() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("nameenameenameenameenameenameenameenameenameenameee");
        productDTO.setDescription("description");
        addProductRequest.setProduct(productDTO);
        addProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(addProductRequest);

        when(productService.add(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ADD_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        AddProductResponse addProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), AddProductResponse.class);

        assert addProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), addProductResponse.getResult());
        assertNotNull(addProductResponse.getErrorCode());
        assertNotNull(addProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), addProductResponse.getErrorCode());
    }

    @Test
    public void add_ShouldReturnValidationException_WhenProductDescriptionIsNull() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("namee");
        addProductRequest.setProduct(productDTO);
        addProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(addProductRequest);

        when(productService.add(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ADD_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        AddProductResponse addProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), AddProductResponse.class);

        assert addProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), addProductResponse.getResult());
        assertNotNull(addProductResponse.getErrorCode());
        assertNotNull(addProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), addProductResponse.getErrorCode());
    }

    @Test
    public void add_ShouldReturnValidationException_WhenProductDescriptionIsEmpty() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("namee");
        productDTO.setDescription(StringUtil.EMPTY_STRING);
        addProductRequest.setProduct(productDTO);
        addProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(addProductRequest);

        when(productService.add(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ADD_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        AddProductResponse addProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), AddProductResponse.class);

        assert addProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), addProductResponse.getResult());
        assertNotNull(addProductResponse.getErrorCode());
        assertNotNull(addProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), addProductResponse.getErrorCode());
    }

    @Test
    public void add_ShouldReturnValidationException_WhenProductDescriptionIsBlankString() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("namee");
        productDTO.setDescription(StringUtil.BLANK_STRING);
        addProductRequest.setProduct(productDTO);
        addProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(addProductRequest);

        when(productService.add(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ADD_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        AddProductResponse addProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), AddProductResponse.class);

        assert addProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), addProductResponse.getResult());
        assertNotNull(addProductResponse.getErrorCode());
        assertNotNull(addProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), addProductResponse.getErrorCode());
    }

    @Test
    public void add_ShouldReturnValidationException_WhenProductDescriptionLengthLessThanMin() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("namee");
        productDTO.setDescription("d");
        addProductRequest.setProduct(productDTO);
        addProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(addProductRequest);

        when(productService.add(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ADD_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        AddProductResponse addProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), AddProductResponse.class);

        assert addProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), addProductResponse.getResult());
        assertNotNull(addProductResponse.getErrorCode());
        assertNotNull(addProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), addProductResponse.getErrorCode());
    }

    @Test
    public void add_ShouldReturnValidationException_WhenProductDescriptionLengthGreaterThanMax() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("namee");
        productDTO.setDescription("descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription");
        addProductRequest.setProduct(productDTO);
        addProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(addProductRequest);

        when(productService.add(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ADD_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        AddProductResponse addProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), AddProductResponse.class);

        assert addProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), addProductResponse.getResult());
        assertNotNull(addProductResponse.getErrorCode());
        assertNotNull(addProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), addProductResponse.getErrorCode());
    }

    @Test
    public void add_ShouldReturnValidationException_WhenProductStockIsNull() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setName("namee");
        productDTO.setDescription("description");
        addProductRequest.setProduct(productDTO);
        addProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(addProductRequest);

        when(productService.add(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ADD_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        AddProductResponse addProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), AddProductResponse.class);

        assert addProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), addProductResponse.getResult());
        assertNotNull(addProductResponse.getErrorCode());
        assertNotNull(addProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), addProductResponse.getErrorCode());
    }

    @Test
    public void add_ShouldReturnValidationException_WhenProductStockIsNegative() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(-1L);
        productDTO.setName("namee");
        productDTO.setDescription("description");
        addProductRequest.setProduct(productDTO);
        addProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(addProductRequest);

        when(productService.add(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ADD_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        AddProductResponse addProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), AddProductResponse.class);

        assert addProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), addProductResponse.getResult());
        assertNotNull(addProductResponse.getErrorCode());
        assertNotNull(addProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), addProductResponse.getErrorCode());
    }

    @Test
    public void add_ShouldReturnValidationException_WhenProductPriceIsNull() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setStock(1L);
        productDTO.setName("namee");
        productDTO.setDescription("description");
        addProductRequest.setProduct(productDTO);
        addProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(addProductRequest);

        when(productService.add(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ADD_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        AddProductResponse addProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), AddProductResponse.class);

        assert addProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), addProductResponse.getResult());
        assertNotNull(addProductResponse.getErrorCode());
        assertNotNull(addProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), addProductResponse.getErrorCode());
    }

    @Test
    public void add_ShouldReturnValidationException_WhenProductPriceLessThan1() throws Exception {
        AddProductRequest addProductRequest = new AddProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(BigDecimal.ZERO);
        productDTO.setStock(1L);
        productDTO.setName("namee");
        productDTO.setDescription("description");
        addProductRequest.setProduct(productDTO);
        addProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(addProductRequest);

        when(productService.add(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(ADD_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        AddProductResponse addProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), AddProductResponse.class);

        assert addProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), addProductResponse.getResult());
        assertNotNull(addProductResponse.getErrorCode());
        assertNotNull(addProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), addProductResponse.getErrorCode());
    }

    @Test
    public void update_ShouldReturnSuccess_WhenThrowNotException() throws Exception {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("namee");
        productDTO.setDescription("description");
        updateProductRequest.setProduct(productDTO);
        updateProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(updateProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        UpdateProductResponse updateProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UpdateProductResponse.class);

        assert updateProductResponse != null;
        assertEquals(Result.SUCCESS.getValue(), updateProductResponse.getResult());
        assertNull(updateProductResponse.getErrorCode());
        assertNull(updateProductResponse.getErrorMessage());
    }

    @Test
    public void update_ShouldReturnValidationException_WhenMerchantIdIsNull() throws Exception {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("namee");
        productDTO.setDescription("description");
        updateProductRequest.setProduct(productDTO);
        String inputJson = super.mapToJson(updateProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        UpdateProductResponse updateProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UpdateProductResponse.class);

        assert updateProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), updateProductResponse.getResult());
        assertNotNull(updateProductResponse.getErrorCode());
        assertNotNull(updateProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), updateProductResponse.getErrorCode());
    }

    @Test
    public void update_ShouldReturnValidationException_WhenProductIdIsNull() throws Exception {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("namee");
        productDTO.setDescription("description");
        updateProductRequest.setProduct(productDTO);
        updateProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(updateProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        UpdateProductResponse updateProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UpdateProductResponse.class);

        assert updateProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), updateProductResponse.getResult());
        assertNotNull(updateProductResponse.getErrorCode());
        assertNotNull(updateProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), updateProductResponse.getErrorCode());
    }

    @Test
    public void update_ShouldReturnValidationException_WhenProductNameIsNull() throws Exception {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setDescription("description");
        updateProductRequest.setProduct(productDTO);
        updateProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(updateProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        UpdateProductResponse updateProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UpdateProductResponse.class);

        assert updateProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), updateProductResponse.getResult());
        assertNotNull(updateProductResponse.getErrorCode());
        assertNotNull(updateProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), updateProductResponse.getErrorCode());
    }

    @Test
    public void update_ShouldReturnValidationException_WhenProductNameIsEmpty() throws Exception {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName(StringUtil.EMPTY_STRING);
        productDTO.setDescription("description");
        updateProductRequest.setProduct(productDTO);
        updateProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(updateProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        UpdateProductResponse updateProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UpdateProductResponse.class);

        assert updateProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), updateProductResponse.getResult());
        assertNotNull(updateProductResponse.getErrorCode());
        assertNotNull(updateProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), updateProductResponse.getErrorCode());
    }

    @Test
    public void update_ShouldReturnValidationException_WhenProductNameIsBlankString() throws Exception {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName(StringUtil.BLANK_STRING);
        productDTO.setDescription("description");
        updateProductRequest.setProduct(productDTO);
        updateProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(updateProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        UpdateProductResponse updateProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UpdateProductResponse.class);

        assert updateProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), updateProductResponse.getResult());
        assertNotNull(updateProductResponse.getErrorCode());
        assertNotNull(updateProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), updateProductResponse.getErrorCode());
    }

    @Test
    public void update_ShouldReturnValidationException_WhenProductNameLengthLessThanMin() throws Exception {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("n");
        productDTO.setDescription("description");
        updateProductRequest.setProduct(productDTO);
        updateProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(updateProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        UpdateProductResponse updateProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UpdateProductResponse.class);

        assert updateProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), updateProductResponse.getResult());
        assertNotNull(updateProductResponse.getErrorCode());
        assertNotNull(updateProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), updateProductResponse.getErrorCode());
    }

    @Test
    public void update_ShouldReturnValidationException_WhenProductNameLengthGreaterThanMax() throws Exception {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("nameenameenameenameenameenameenameenameenameenameee");
        productDTO.setDescription("description");
        updateProductRequest.setProduct(productDTO);
        updateProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(updateProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        UpdateProductResponse updateProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UpdateProductResponse.class);

        assert updateProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), updateProductResponse.getResult());
        assertNotNull(updateProductResponse.getErrorCode());
        assertNotNull(updateProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), updateProductResponse.getErrorCode());
    }

    @Test
    public void update_ShouldReturnValidationException_WhenProductDescriptionIsNull() throws Exception {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("namee");
        updateProductRequest.setProduct(productDTO);
        updateProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(updateProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        UpdateProductResponse updateProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UpdateProductResponse.class);

        assert updateProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), updateProductResponse.getResult());
        assertNotNull(updateProductResponse.getErrorCode());
        assertNotNull(updateProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), updateProductResponse.getErrorCode());
    }

    @Test
    public void update_ShouldReturnValidationException_WhenProductDescriptionIsEmpty() throws Exception {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("namee");
        productDTO.setDescription(StringUtil.EMPTY_STRING);
        updateProductRequest.setProduct(productDTO);
        updateProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(updateProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        UpdateProductResponse updateProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UpdateProductResponse.class);

        assert updateProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), updateProductResponse.getResult());
        assertNotNull(updateProductResponse.getErrorCode());
        assertNotNull(updateProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), updateProductResponse.getErrorCode());
    }

    @Test
    public void update_ShouldReturnValidationException_WhenProductDescriptionIsBlankString() throws Exception {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("namee");
        productDTO.setDescription(StringUtil.BLANK_STRING);
        updateProductRequest.setProduct(productDTO);
        updateProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(updateProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        UpdateProductResponse updateProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UpdateProductResponse.class);

        assert updateProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), updateProductResponse.getResult());
        assertNotNull(updateProductResponse.getErrorCode());
        assertNotNull(updateProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), updateProductResponse.getErrorCode());
    }

    @Test
    public void update_ShouldReturnValidationException_WhenProductDescriptionLengthLessThanMin() throws Exception {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("namee");
        productDTO.setDescription("d");
        updateProductRequest.setProduct(productDTO);
        updateProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(updateProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        UpdateProductResponse updateProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UpdateProductResponse.class);

        assert updateProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), updateProductResponse.getResult());
        assertNotNull(updateProductResponse.getErrorCode());
        assertNotNull(updateProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), updateProductResponse.getErrorCode());
    }

    @Test
    public void update_ShouldReturnValidationException_WhenProductDescriptionLengthGreaterThanMax() throws Exception {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(1L);
        productDTO.setName("namee");
        productDTO.setDescription("descriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescriptiondescription");
        updateProductRequest.setProduct(productDTO);
        updateProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(updateProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        UpdateProductResponse updateProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UpdateProductResponse.class);

        assert updateProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), updateProductResponse.getResult());
        assertNotNull(updateProductResponse.getErrorCode());
        assertNotNull(updateProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), updateProductResponse.getErrorCode());
    }

    @Test
    public void update_ShouldReturnValidationException_WhenProductStockIsNull() throws Exception {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setName("namee");
        productDTO.setDescription("description");
        updateProductRequest.setProduct(productDTO);
        updateProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(updateProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        UpdateProductResponse updateProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UpdateProductResponse.class);

        assert updateProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), updateProductResponse.getResult());
        assertNotNull(updateProductResponse.getErrorCode());
        assertNotNull(updateProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), updateProductResponse.getErrorCode());
    }

    @Test
    public void update_ShouldReturnValidationException_WhenProductStockIsNegative() throws Exception {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(BigDecimal.ONE);
        productDTO.setStock(-1L);
        productDTO.setName("namee");
        productDTO.setDescription("description");
        updateProductRequest.setProduct(productDTO);
        updateProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(updateProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        UpdateProductResponse updateProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UpdateProductResponse.class);

        assert updateProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), updateProductResponse.getResult());
        assertNotNull(updateProductResponse.getErrorCode());
        assertNotNull(updateProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), updateProductResponse.getErrorCode());
    }

    @Test
    public void update_ShouldReturnValidationException_WhenProductPriceIsNull() throws Exception {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setStock(1L);
        productDTO.setName("namee");
        productDTO.setDescription("description");
        updateProductRequest.setProduct(productDTO);
        updateProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(updateProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        UpdateProductResponse updateProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UpdateProductResponse.class);

        assert updateProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), updateProductResponse.getResult());
        assertNotNull(updateProductResponse.getErrorCode());
        assertNotNull(updateProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), updateProductResponse.getErrorCode());
    }

    @Test
    public void update_ShouldReturnValidationException_WhenProductPriceLessThan1() throws Exception {
        UpdateProductRequest updateProductRequest = new UpdateProductRequest();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setPrice(BigDecimal.ZERO);
        productDTO.setStock(1L);
        productDTO.setName("namee");
        productDTO.setDescription("description");
        updateProductRequest.setProduct(productDTO);
        updateProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(updateProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(UPDATE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        UpdateProductResponse updateProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), UpdateProductResponse.class);

        assert updateProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), updateProductResponse.getResult());
        assertNotNull(updateProductResponse.getErrorCode());
        assertNotNull(updateProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), updateProductResponse.getErrorCode());
    }

    @Test
    public void delete_ShouldReturnSuccess_WhenThrowNotException() throws Exception {
        DeleteProductRequest deleteProductRequest = new DeleteProductRequest();
        deleteProductRequest.setProductId(1L);
        deleteProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(deleteProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(DELETE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        DeleteProductResponse deleteProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), DeleteProductResponse.class);

        assert deleteProductResponse != null;
        assertEquals(Result.SUCCESS.getValue(), deleteProductResponse.getResult());
        assertNull(deleteProductResponse.getErrorCode());
        assertNull(deleteProductResponse.getErrorMessage());
    }

    @Test
    public void delete_ShouldReturnValidationException_WhenMerchantIdIsNull() throws Exception {
        DeleteProductRequest deleteProductRequest = new DeleteProductRequest();
        deleteProductRequest.setProductId(1L);
        String inputJson = super.mapToJson(deleteProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(DELETE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        DeleteProductResponse deleteProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), DeleteProductResponse.class);

        assert deleteProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), deleteProductResponse.getResult());
        assertNotNull(deleteProductResponse.getErrorCode());
        assertNotNull(deleteProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), deleteProductResponse.getErrorCode());
    }

    @Test
    public void delete_ShouldReturnValidationException_WhenProductIdIsNull() throws Exception {
        DeleteProductRequest deleteProductRequest = new DeleteProductRequest();
        deleteProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(deleteProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(DELETE_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        DeleteProductResponse deleteProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), DeleteProductResponse.class);

        assert deleteProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), deleteProductResponse.getResult());
        assertNotNull(deleteProductResponse.getErrorCode());
        assertNotNull(deleteProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), deleteProductResponse.getErrorCode());
    }

    @Test
    public void list_ShouldReturnSuccess_WhenThrowNotException() throws Exception {
        ListProductRequest listProductRequest = new ListProductRequest();
        listProductRequest.setMerchantId(1L);
        String inputJson = super.mapToJson(listProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(LIST_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        ListProductResponse listProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), ListProductResponse.class);

        assert listProductResponse != null;
        assertEquals(Result.SUCCESS.getValue(), listProductResponse.getResult());
        assertNull(listProductResponse.getErrorCode());
        assertNull(listProductResponse.getErrorMessage());
    }

    @Test
    public void list_ShouldReturnValidationException_WhenMerchantIdIsNull() throws Exception {
        ListProductRequest listProductRequest = new ListProductRequest();
        String inputJson = super.mapToJson(listProductRequest);

        when(productService.update(any(), anyLong())).thenReturn(new ProductDTO());
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(LIST_URL)
                .headers(new HttpHeaders())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();
        ListProductResponse listProductResponse = super.mapFromJson(mvcResult.getResponse().getContentAsString(), ListProductResponse.class);

        assert listProductResponse != null;
        assertEquals(Result.FAILURE.getValue(), listProductResponse.getResult());
        assertNotNull(listProductResponse.getErrorCode());
        assertNotNull(listProductResponse.getErrorMessage());
        assertEquals(ErrorConstant.VALIDATION_ERROR.getErrorCode(), listProductResponse.getErrorCode());
    }
}
