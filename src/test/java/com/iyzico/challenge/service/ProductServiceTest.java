package com.iyzico.challenge.service;

import com.iyzico.challenge.constant.ErrorConstant;
import com.iyzico.challenge.dto.ProductDTO;
import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.exception.BusinessException;
import com.iyzico.challenge.mapper.ProductMapper;
import com.iyzico.challenge.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;

import javax.persistence.OptimisticLockException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private PaymentServiceClients paymentServiceClients;
    @Mock
    private Logger logger;

    @Before
    public void setUp() {
        initMocks(this);
        doNothing().when(logger).info(anyString());
    }

    @Test
    public void add_ShouldInsertProduct_WhenProductDtoAndMerchantIdIsNotNull() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("name");
        productDTO.setDescription("description");
        productDTO.setStock(1l);
        productDTO.setPrice(BigDecimal.ONE);
        Product product = new Product();
        product.setId(1l);

        when(productMapper.mapToProduct(productDTO, 1l)).thenReturn(product);
        when(productMapper.mapToProductDTO(product)).thenReturn(productDTO);
        when(productRepository.save(product)).thenReturn(product);
        productService.add(productDTO, 1l);

        verify(productRepository, times(1)).save(any());
    }

    @Test
    public void update_ShouldUpdateProduct_WhenProductIsFound() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1l);
        Product product = new Product();
        product.setId(1l);

        when(productMapper.mapToProductDTO(product)).thenReturn(productDTO);
        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        productService.update(productDTO, 1l);

        verify(productRepository, times(1)).save(any());
    }

    @Test(expected = BusinessException.class)
    public void update_ShouldThrowBusinessException_WhenProductIsNotFound() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1l);
        Product product = new Product();
        product.setId(1l);

        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenThrow(BusinessException.class);
        productService.update(productDTO, 1l);
    }

    @Test
    public void update_ShouldReturnProductNotFoundError_WhenProductIsNotFound() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1l);
        Product product = new Product();
        product.setId(1l);

        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenThrow(new BusinessException(ErrorConstant.PRODUCT_NOT_FOUND_ERROR));
        try {
            productService.update(productDTO, 1l);
        } catch (BusinessException e) {
            Assert.assertEquals(ErrorConstant.PRODUCT_NOT_FOUND_ERROR.getErrorCode(), e.getError().getErrorCode());
        }
    }

    @Test
    public void delete_ShouldDeleteProduct_WhenProductIsFound() {
        Product product = new Product();
        product.setId(1l);

        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).delete(product);
        productService.delete(1l, 1l);

        verify(productRepository, times(1)).delete(any());
    }

    @Test(expected = BusinessException.class)
    public void delete_ShouldThrowBusinessException_WhenProductIsNotFound() {
        Product product = new Product();
        product.setId(1l);

        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenThrow(BusinessException.class);
        doNothing().when(productRepository).delete(product);
        productService.delete(1l, 1l);
    }

    @Test
    public void delete_ShouldReturnProductNotFoundError_WhenProductIsNotFound() {
        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenThrow(new BusinessException(ErrorConstant.PRODUCT_NOT_FOUND_ERROR));
        try {
            productService.delete(1l, 1l);
        } catch (BusinessException e) {
            Assert.assertEquals(ErrorConstant.PRODUCT_NOT_FOUND_ERROR.getErrorCode(), e.getError().getErrorCode());

        }
    }

    @Test
    public void list_ShouldReturnProductList_WhenProductsAreFound() {
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();

        when(productRepository.findByMerchantId(1l)).thenReturn(Optional.of(Collections.singletonList(product)));
        when(productMapper.mapToProductDTO(product)).thenReturn(productDTO);
        List<ProductDTO> productList = productService.list(1l);

        Assert.assertEquals(1, productList.size());
    }

    @Test
    public void list_ShouldReturnEmptyProductList_WhenProductsAreNotFound() {
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();

        when(productRepository.findByMerchantId(1l)).thenReturn(Optional.empty());
        when(productMapper.mapToProductDTO(product)).thenReturn(productDTO);
        List<ProductDTO> productList = productService.list(1l);

        Assert.assertEquals(0, productList.size());
    }

    @Test
    public void findByIdAndMerchantId_ShouldReturnProductDTO_WhenProductIsFound() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1l);
        Product product = new Product();
        product.setId(1l);

        when(productMapper.mapToProductDTO(product)).thenReturn(productDTO);
        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenReturn(Optional.of(product));
        ProductDTO result = productService.findByIdAndMerchantId(1l, 1l);

        Assert.assertNotEquals(null, result);
    }

    @Test(expected = BusinessException.class)
    public void findByIdAndMerchantId_ShouldThrowBusinessException_WhenProductIsNotFound() {

        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenThrow(BusinessException.class);
        productService.findByIdAndMerchantId(1l, 1l);
    }

    @Test
    public void findByIdAndMerchantId_ShouldReturnProductNotFoundError_WhenProductIsNotFound() {

        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenThrow(new BusinessException(ErrorConstant.PRODUCT_NOT_FOUND_ERROR));
        try {
            productService.findByIdAndMerchantId(1l, 1l);
        } catch (BusinessException e) {
            Assert.assertEquals(ErrorConstant.PRODUCT_NOT_FOUND_ERROR.getErrorCode(), e.getError().getErrorCode());
        }
    }

    @Test(expected = BusinessException.class)
    public void checkStock_ShouldThrowBusinessException_WhenNoProductStock() {
        Product product = new Product();
        product.setStock(0l);

        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenReturn(Optional.of(product));
        productService.checkStock(1l, 1l, 1);
    }

    @Test
    public void checkStock_ShouldReturnNoProductInStockError_WhenNoProductStock() {
        Product product = new Product();
        product.setStock(0l);

        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenReturn(Optional.of(product));
        try {
            productService.checkStock(1l, 1l, 1);
        } catch (BusinessException e) {
            Assert.assertEquals(ErrorConstant.NO_PRODUCT_IN_STOCK.getErrorCode(), e.getError().getErrorCode());
        }
    }

    @Test(expected = BusinessException.class)
    public void checkStock_ShouldThrowBusinessException_WhenProductIsNotFound() {

        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenThrow(BusinessException.class);
        productService.checkStock(1l, 1l, 1);
    }

    @Test
    public void checkStock_ShouldReturnProductNotFoundError_WhenProductIsNotFound() {

        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenThrow(new BusinessException(ErrorConstant.PRODUCT_NOT_FOUND_ERROR));
        try {
            productService.checkStock(1l, 1l, 1);
        } catch (BusinessException e) {
            Assert.assertEquals(ErrorConstant.PRODUCT_NOT_FOUND_ERROR.getErrorCode(), e.getError().getErrorCode());
        }
    }

    @Test
    public void checkStock_ShouldNotThrowException_WhenProductInStock() {
        Product product = new Product();
        product.setStock(1l);

        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenReturn(Optional.of(product));
        productService.checkStock(1l, 1l, 1);
    }

    @Test
    public void updateStock_ShouldUpdateProduct_WhenProductIsFound() {
        Product product = new Product();
        product.setStock(1l);

        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        productService.updateStock(1l, 1l, 1);

        verify(productRepository, times(1)).save(any());
    }

    @Test
    public void updateStock_ShouldReturnTrue_WhenProductIsSaved() {
        Product product = new Product();
        product.setStock(1l);

        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        Boolean result = productService.updateStock(1l, 1l, 1);

        Assert.assertEquals(Boolean.TRUE, result);
    }

    @Test
    public void updateStock_ShouldReturnFalse_WhenThrowOptimisticLockException() {
        Product product = new Product();
        product.setStock(1l);

        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenThrow(OptimisticLockException.class);
        Boolean result = productService.updateStock(1l, 1l, 1);

        Assert.assertEquals(Boolean.FALSE, result);
    }

    @Test(expected = BusinessException.class)
    public void updateStock_ShouldThrowBusinessException_WhenProductIsNotFound() {

        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenThrow(BusinessException.class);
        productService.updateStock(1l, 1l, 1);
    }

    @Test
    public void updateStock_ShouldReturnProductNotFoundError_WhenProductIsNotFound() {

        when(productRepository.findByIdAndMerchantId(1l, 1l)).thenThrow(new BusinessException(ErrorConstant.PRODUCT_NOT_FOUND_ERROR));
        try {
            productService.updateStock(1l, 1l, 1);
        } catch (BusinessException e) {
            Assert.assertEquals(ErrorConstant.PRODUCT_NOT_FOUND_ERROR.getErrorCode(), e.getError().getErrorCode());
        }
    }
}
