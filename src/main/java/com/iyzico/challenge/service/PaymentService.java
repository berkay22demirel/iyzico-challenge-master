package com.iyzico.challenge.service;

import com.iyzico.challenge.constant.Result;
import com.iyzico.challenge.dto.PaymentDTO;
import com.iyzico.challenge.dto.ProductDTO;
import com.iyzico.challenge.repository.PaymentRepository;
import com.iyzipay.model.Cancel;
import com.iyzipay.model.Payment;
import com.iyzipay.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {

    private Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;
    private final IyzicoPaymentProvider iyzicoPaymentProvider;
    private final ProductService productService;
    private final int LOCKED_PRODUCT_PAYMENT_TRY_COUNT = 3;

    public PaymentService(PaymentRepository paymentRepository, IyzicoPaymentProvider iyzicoPaymentProvider, ProductService productService) {
        this.paymentRepository = paymentRepository;
        this.iyzicoPaymentProvider = iyzicoPaymentProvider;
        this.productService = productService;
    }

    public PaymentDTO pay(Long merchantId, Long productId, Integer productQuantity, String locale) {
        ProductDTO product = productService.findByIdAndMerchantId(productId, merchantId);
        productService.checkStock(productId, merchantId, productQuantity);
        String orderId = UUID.randomUUID().toString();
        BigDecimal totalAmount = product.getPrice().multiply(BigDecimal.valueOf(productQuantity));
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setResult(Result.SUCCESS.getValue());
        paymentDTO.setTotalAmount(totalAmount);
        paymentDTO.setOrderId(orderId);
        paymentDTO.setProduct(product);
        paymentDTO.setProductQuantity(productQuantity);
        Payment iyzicoPaymentResponse = iyzicoPaymentProvider.pay(totalAmount, product, orderId, locale);
        try {
            if (Status.SUCCESS.getValue().equals(iyzicoPaymentResponse.getStatus())) {
                int tryCount = 0;
                while (!productService.updateStock(productId, merchantId, productQuantity)) {
                    tryCount++;
                    if (tryCount > LOCKED_PRODUCT_PAYMENT_TRY_COUNT) {
                        paymentDTO.setResult(Result.FAILURE.getValue());
                        cancel(orderId, iyzicoPaymentResponse.getPaymentId(), locale);
                        break;
                    }
                }
            } else {
                paymentDTO.setResult(Result.FAILURE.getValue());
            }
            logger.error("Order Id : {} - Payment Id : {} - Payment Result : {} - Payment completed!", orderId, iyzicoPaymentResponse.getPaymentId(), iyzicoPaymentResponse.getStatus());
            savePayment(paymentDTO, merchantId);
            return paymentDTO;
        } catch (Exception e) {
            logger.error("Order Id : {} - Payment Id : {} - An unexpected error occurred while saving the payment!", orderId, iyzicoPaymentResponse.getPaymentId());
            paymentDTO.setResult(Result.FAILURE.getValue());
            cancel(orderId, iyzicoPaymentResponse.getPaymentId(), locale);
            savePayment(paymentDTO, merchantId);
            return paymentDTO;
        }
    }

    public void cancel(String orderId, String paymentId, String locale) {
        try {
            Cancel iyzicoCancelResponse = iyzicoPaymentProvider.cancel(orderId, paymentId, locale);
            if (Status.SUCCESS.getValue().equals(iyzicoCancelResponse.getStatus())) {
                logger.error("Order Id : {} - Payment Id : {} - Payment canceled successfully!", orderId, paymentId);
            } else {
                logger.error("Order Id : {} - Payment Id : {} - Payment not canceled successfully!", orderId, paymentId);
            }
        } catch (Exception e) {
            logger.error("Order Id : {} - Payment Id : {} - Payment not canceled successfully!", orderId, paymentId);
        }
    }

    private void savePayment(PaymentDTO paymentDTO, Long merchantId) {
        com.iyzico.challenge.entity.Payment payment = new com.iyzico.challenge.entity.Payment();
        payment.setPrice(paymentDTO.getTotalAmount());
        payment.setOrderId(paymentDTO.getOrderId());
        payment.setBankResponse(paymentDTO.getResult().toString());
        payment.setMerchantId(merchantId);
        paymentRepository.save(payment);
    }

}
