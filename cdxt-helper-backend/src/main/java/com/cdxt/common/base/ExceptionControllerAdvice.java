package com.cdxt.common.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cdxt.common.exception.ResponseCommonException;
import com.cdxt.common.pojo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * @Author: TengGang
 * @Date: 2019/7/7 13:20
 * @Description:
 */
@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    JSONObject info = new JSONObject();

    @ExceptionHandler
    public void processExceptions(HttpServletRequest request, HttpServletResponse response, Exception e){
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,token");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        ResultVO resultVO = new ResultVO();
        if( e instanceof ResponseCommonException){
            resultVO.setCode(((ResponseCommonException) e).getHttpStatus().value());
            resultVO.setMsg(e.getMessage());
        }else{
            log.error("服务器内部异常:" , e);
            resultVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultVO.setMsg("亲,服务器正在抢修中,请稍后再来...");
            resultVO.setData(e);
        }
        try (PrintWriter writer = response.getWriter()){
            writer.print(JSON.toJSON(resultVO));
            writer.flush();
        } catch (IOException ioe) {
            log.error("服务器内部异常:" ,e);
        }
    }


}
