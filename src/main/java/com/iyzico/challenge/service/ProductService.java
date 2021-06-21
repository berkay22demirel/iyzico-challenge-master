package com.iyzico.challenge.service;

import com.iyzico.challenge.constant.ErrorConstant;
import com.iyzico.challenge.dto.ProductDTO;
import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.exception.BusinessException;
import com.iyzico.challenge.mapper.ProductMapper;
import com.iyzico.challenge.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private Logger logger = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository productRepository;
    private ProductMapper productMapper;
    private PaymentServiceClients paymentServiceClients;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper, PaymentServiceClients paymentServiceClients) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.paymentServiceClients = paymentServiceClients;
    }

    public ProductDTO add(ProductDTO productDTO, Long merchantId) {
        Product product = productMapper.mapToProduct(productDTO, merchantId);
        product = productRepository.save(product);
        logger.info("Product Id : {} - Product saved successfully!", product.getId());
        return productMapper.mapToProductDTO(product);
    }

    public ProductDTO update(ProductDTO productDTO, Long merchantId) throws BusinessException {
        Product product = findProductByIdAndMerchantId(productDTO.getId(), merchantId);
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        productRepository.save(product);
        logger.info("Product Id : {} - Product updated successfully!", product.getId());
        return productMapper.mapToProductDTO(product);
    }

    public void delete(Long productId, Long merchantId) {
        Product product = findProductByIdAndMerchantId(productId, merchantId);
        productRepository.delete(product);
        logger.info("Product Id : {} - Product deleted successfully!", product.getId());
    }

    public List<ProductDTO> list(Long merchantId) {
        return productRepository.findByMerchantId(merchantId)
                .orElse(new ArrayList<>())
                .stream()
                .map(product -> productMapper.mapToProductDTO(product))
                .collect(Collectors.toList());
    }

    public ProductDTO findByIdAndMerchantId(Long productId, Long merchantId) throws BusinessException {
        return productMapper.mapToProductDTO(findProductByIdAndMerchantId(productId, merchantId));
    }


    public void checkStock(Long productId, Long merchantId, Integer productQuantity) {
        Product product = findProductByIdAndMerchantId(productId, merchantId);
        if (product.getStock() < productQuantity) {
            throw new BusinessException(ErrorConstant.NO_PRODUCT_IN_STOCK);
        }
    }

    public void updateStock(Long productId, Long merchantId, Integer productQuantity) {
        Product product = findProductByIdAndMerchantId(productId, merchantId);
        product.setStock(product.getStock() - productQuantity);
        productRepository.save(product);
    }

    private Product findProductByIdAndMerchantId(Long productId, Long merchantId) throws BusinessException {
        return productRepository.findByIdAndMerchantId(productId, merchantId)
                .orElseThrow(() -> new BusinessException(ErrorConstant.PRODUCT_NOT_FOUND_ERROR));
    }
}
