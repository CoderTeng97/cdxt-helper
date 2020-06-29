package com.cdxt.common.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserBaseVO {

    private String id;
    private String username;
    private String role;
    private String token;
}
