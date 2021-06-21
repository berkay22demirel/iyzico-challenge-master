package com.iyzico.challenge.service;

import com.iyzico.challenge.dto.ProductDTO;
import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.request.CreateCancelRequest;
import com.iyzipay.request.CreatePaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class IyzicoPaymentProvider {

    private Logger logger = LoggerFactory.getLogger(IyzicoPaymentProvider.class);

    @Autowired
    private Environment env;
    private Options options;

    @PostConstruct
    public void setUp() {
        options = new Options();
        options.setApiKey(env.getProperty("iyzico.api-key"));
        options.setSecretKey(env.getProperty("iyzico.secret-key"));
        options.setBaseUrl(env.getProperty("iyzico.base-url"));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Payment pay(BigDecimal price, ProductDTO product, String orderId, String locale) {
        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setLocale(locale);
        request.setConversationId(orderId);
        request.setPrice(price);
        request.setPaidPrice(price);
        request.setCurrency(Currency.TRY.name());
        request.setInstallment(1);
        request.setBasketId("B67832");
        request.setPaymentChannel(PaymentChannel.WEB.name());
        request.setPaymentGroup(PaymentGroup.PRODUCT.name());

        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardHolderName("John Doe");
        paymentCard.setCardNumber("5528790000000008");
        paymentCard.setExpireMonth("12");
        paymentCard.setExpireYear("2030");
        paymentCard.setCvc("123");
        paymentCard.setRegisterCard(0);
        request.setPaymentCard(paymentCard);

        Buyer buyer = new Buyer();
        buyer.setId("BY789");
        buyer.setName("Berkay");
        buyer.setSurname("Demirel");
        buyer.setGsmNumber("+905350000000");
        buyer.setEmail("berkay_demirel22@hotmail.com");
        buyer.setIdentityNumber("74300864791");
        buyer.setLastLoginDate("2015-10-05 12:43:35");
        buyer.setRegistrationDate("2013-04-21 15:12:09");
        buyer.setRegistrationAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        buyer.setIp("85.34.78.112");
        buyer.setCity("Istanbul");
        buyer.setCountry("Turkey");
        buyer.setZipCode("34732");
        request.setBuyer(buyer);

        Address shippingAddress = new Address();
        shippingAddress.setContactName("Jane Doe");
        shippingAddress.setCity("Istanbul");
        shippingAddress.setCountry("Turkey");
        shippingAddress.setAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        shippingAddress.setZipCode("34742");
        request.setShippingAddress(shippingAddress);

        Address billingAddress = new Address();
        billingAddress.setContactName("Jane Doe");
        billingAddress.setCity("Istanbul");
        billingAddress.setCountry("Turkey");
        billingAddress.setAddress("Nidakule Göztepe, Merdivenköy Mah. Bora Sok. No:1");
        billingAddress.setZipCode("34742");
        request.setBillingAddress(billingAddress);

        List<BasketItem> basketItems = new ArrayList<>();
        BasketItem basketItem = new BasketItem();
        basketItem.setId("BI101");
        basketItem.setName(product.getName());
        basketItem.setCategory1("Collectibles");
        basketItem.setCategory2("Accessories");
        basketItem.setItemType(BasketItemType.PHYSICAL.name());
        basketItem.setPrice(product.getPrice());
        basketItems.add(basketItem);
        request.setBasketItems(basketItems);

        Payment payment = Payment.create(request, options);

        logger.info("Payment Id : {} - Order Id : {} - Payment Result : {} - Iyzico ödemesi yapıldı.", payment.getPaymentId(), payment.getConversationId(), payment.getStatus());

        return payment;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Cancel cancel(String orderId, String paymentId, String locale) {
        CreateCancelRequest request = new CreateCancelRequest();
        request.setLocale(locale);
        request.setConversationId(orderId);
        request.setPaymentId(paymentId);
        request.setIp("85.34.78.112");

        Cancel cancel = Cancel.create(request, options);

        logger.info("Payment Id : {} - Order Id : {} - Cancel Payment Result : {} - Iyzico ödeme iptali yapıldı.", cancel.getPaymentId(), cancel.getConversationId(), cancel.getStatus());

        return cancel;
    }

}
