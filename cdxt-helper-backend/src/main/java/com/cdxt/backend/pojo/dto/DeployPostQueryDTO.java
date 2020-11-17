package com.cdxt.backend.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: TengGang
 * @Date: 2020/6/21 16:34
 * @Description:  部署列表查询DTO
 */
@ApiModel
@Data
public class DeployPostQueryDTO {
    @ApiModelProperty(value = "医院id",required = false)
    private  String  hospitalId;
    @ApiModelProperty(value = "记录状态",required = false)
    private  Integer state;
    @ApiModelProperty(value = "页码",required = false)
    private Integer pageNum = 1;
    @ApiModelProperty(value = "总页数",required = false)
    private Integer pageSize = 10;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "发布开始时间",required = false)
    private LocalDateTime postStartTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "发布结束时间",required = false)
    private LocalDateTime postEndTime;
    @ApiModelProperty(value = "用户id",hidden =  true)
    private String uid;
    @ApiModelProperty(value = "用户权限",hidden =  true)
    private String userRole;

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
