package com.iyzico.challenge.controller;

import com.iyzico.challenge.constant.ErrorConstant;
import com.iyzico.challenge.constant.Result;
import com.iyzico.challenge.controller.response.BaseResponse;
import com.iyzico.challenge.exception.BusinessException;
import com.iyzico.challenge.util.ExceptionUtil;
import com.iyzico.challenge.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class ControllerExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    private final MessageSource messageSource;

    public ControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<BaseResponse> handle(BusinessException e, Locale locale) {
        BaseResponse response = new BaseResponse();
        response.setResult(Result.FAILURE.getValue());
        response.setErrorCode(e.getError().getErrorCode());
        response.setErrorMessage(messageSource.getMessage(e.getError().getErrorMessageKey(), new Object[]{}, locale));
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handle(MethodArgumentNotValidException e, Locale locale) {
        String messageKey = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(StringUtil.EMPTY_STRING);
        BaseResponse response = new BaseResponse();
        response.setResult(Result.FAILURE.getValue());
        response.setErrorCode(ErrorConstant.VALIDATION_ERROR.getErrorCode());
        response.setErrorMessage(messageSource.getMessage(messageKey, new Object[]{}, locale));
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<BaseResponse> handle(Exception e, Locale locale) {
        BaseResponse response = new BaseResponse();
        response.setResult(Result.FAILURE.getValue());
        response.setErrorCode(ErrorConstant.UNEXPECTED_ERROR.getErrorCode());
        response.setErrorMessage(messageSource.getMessage(ErrorConstant.UNEXPECTED_ERROR.getErrorMessageKey(), new Object[]{}, locale));
        logger.error(ExceptionUtil.getErrorString(e));
        return ResponseEntity.ok(response);
    }
}
