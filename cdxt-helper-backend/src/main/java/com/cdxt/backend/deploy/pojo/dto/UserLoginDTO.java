package com.cdxt.backend.deploy.pojo.dto;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Data
public class UserLoginDTO {
    private String account;
    private String password;
}
