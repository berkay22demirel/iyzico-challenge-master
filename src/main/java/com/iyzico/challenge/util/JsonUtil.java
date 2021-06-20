package com.iyzico.challenge.util;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JsonUtil {

    private Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public <T> String convertToJson(T value) {
        String json = null;
        try {
            Gson gson = new Gson();
            json = gson.toJson(value);
        } catch (Exception e) {
            logger.error(ExceptionUtil.getErrorString(e));
        }
        return json;
    }
}
