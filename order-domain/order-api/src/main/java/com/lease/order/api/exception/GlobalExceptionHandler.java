package com.lease.order.api.exception;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RetryableException.class)
    public JSONObject handleRetryableException(RetryableException e) {
        log.warn(String.format("远程调用错误 ex=%s", e.getMessage()), e);
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "远程服务错误! 请稍后重试");
    }

    /**
     * 请求的 JSON 参数在请求体内的参数校验
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public JSONObject handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(","));
        log.warn("参数校验异常信息 ex={}", e.getMessage());
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public JSONObject httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("http参数转化错误: {}", e.getMessage());
        String message;
        if (e.contains(InvalidFormatException.class)) {
            message = "参数格式错误";
        } else {
            message = e.getMessage();
        }
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    @ExceptionHandler(Exception.class)
    public JSONObject handleException(Exception e) {
        log.error(String.format("自定义异常信息 ex=%s", e.getMessage()), e);
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public JSONObject handleServiceException(ServiceException e) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    public static JSONObject error(int code, String message) {
        return new JSONObject().fluentPut("code", code).fluentPut("msg", message);
    }
}