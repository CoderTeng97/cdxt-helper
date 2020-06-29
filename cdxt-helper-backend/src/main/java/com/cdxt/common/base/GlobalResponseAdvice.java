package com.cdxt.common.base;


import com.alibaba.fastjson.JSON;
import com.cdxt.common.annotation.ControllerResponseIgnore;
import com.cdxt.common.annotation.ControllerResponseProcessor;
import com.cdxt.common.pojo.vo.ResultVO;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;

/**
 * @Author: TengGang
 * @Date: 2019/7/7 13:48
 * @Description: 对controller层返回成功的结果进行包装
 */
@ControllerAdvice(basePackages = {"com.cdxt"})
public class GlobalResponseAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        //是否忽略该方法
        Method method = returnType.getMethod();
        ControllerResponseIgnore controllerResponseIgnore  = method.getAnnotation(ControllerResponseIgnore.class);
        if (controllerResponseIgnore != null){
            return  false;
        }
        //查看请求类是否有ControllerExceptionProcessor注解
        Class clazz = returnType.getContainingClass();
        //Class superClazz = clazz.getSuperclass();
        ControllerResponseProcessor exceptionProcessor = (ControllerResponseProcessor) clazz.getAnnotation(ControllerResponseProcessor.class);
        if(exceptionProcessor != null){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(HttpStatus.OK.value());
        resultVO.setMsg(HttpStatus.OK.getReasonPhrase());
        resultVO.setData(body);
        return resultVO ;
    }
}
