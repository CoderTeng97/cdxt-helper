package com.cdxt.common.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@ToString
@AllArgsConstructor
public class TokenVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String token;
}
