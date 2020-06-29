package com.cdxt.common.pojo.vo;

import lombok.Data;

/**
 * 通用返回结果类
 */
@Data
public class ResultVO {
    private String msg;
    private Integer code;
    private Object data;
}
