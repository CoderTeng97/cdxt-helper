package com.cdxt.backend.deploy.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*
 * @Author: TengGang
 * @Date: 2020/6/7 21:53
 * @Description:  医院分支更新
 */
@ApiModel
@Data
public class HospitalUpdateDTO {

    @ApiModelProperty(value = "医院id(为空默认新增)",required = false)
    private String id;
    /**
     * 医院名称
     */
    @ApiModelProperty(value = "医院名称)",required = true)
    private String name;

    /**
     * 对应分支名称
     */
    @ApiModelProperty(value = "医院对应分支)",required = true)
    private String branch;

}
