package com.fuhan.v17center.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuhan.commons.pojo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : FuHan
 * @description : ***
 * @date: 2019/10/22
 */
@ControllerAdvice
@Slf4j
public class GloablExceptionHandler {

    /**
     * 做一个全局的异常处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultBean<String> handlerException(Exception e){
        log.error(e.getMessage());
        return new ResultBean<>("success","当前服务器繁忙，请稍后再试");
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResultBean<String> validationErrorHandler(BindException ex) throws JsonProcessingException {
        //1.此处先获取BindingResult
        BindingResult bindingResult = ex.getBindingResult();
        //2.获取错误信息
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        //3.组装异常信息
        Map<String,String> message = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            message.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        //4.将map转换为JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(message);
        //5.返回错误信息
        return new ResultBean<>("400",json);
    }
}
