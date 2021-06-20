package com.iyzico.challenge.controller.request;

import com.iyzico.challenge.validation.Create;
import com.iyzico.challenge.validation.Get;
import com.iyzico.challenge.validation.Update;

import javax.validation.constraints.NotNull;

public abstract class BaseRequest {

    @NotNull(message = "merchant_id.not_null", groups = {Update.class, Create.class, Get.class})
    @NotNull(message = "merchant_id.not_null")
    private Long merchantId;

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
}
