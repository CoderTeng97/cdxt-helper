package com.cdxt.common.utils;

import com.cdxt.common.exception.ResponseCommonException;
import com.cdxt.common.pojo.vo.ResultVO;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class ResultVOUtil<T> {
    /**
     * 判断是否请求成功
     * @param vo
     * @return
     */
    public static Boolean isSuccess(ResultVO vo){
        Optional<Integer> op = Optional.ofNullable(vo.getCode());
        if (op.isPresent()){
            if ("200".equals(op.get())){
                return true;
            }else{
                return false;
            }
        }else{
            throw  new ResponseCommonException(HttpStatus.BAD_REQUEST,"服务调用失败，请稍后重试");
        }
    }

    /**
     * 获取结果VO中的值
     * @param vo
     * @return
     */
    public static Object get(ResultVO vo){
        Boolean isSuccess = isSuccess(vo);
        if (isSuccess){
            return vo;
        }else{
            throw  new ResponseCommonException(HttpStatus.NOT_FOUND,"没有找到您服务调用的值，请稍后重试~");
        }
    }


}
