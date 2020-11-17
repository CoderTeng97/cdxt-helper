package com.cdxt.backend.pojo.dto;


import lombok.Data;

import java.util.List;

@Data
public class DeployPostDTO {

    /**
     * 医院id
     */
    private String hospitalId;

    /**
     * 发布分支
     */
    private String branch;

    /**
     * 备注
     */
    private String remark;

    /**
     * 0 一般 1 紧急 2 不紧急
     */
    private Integer postLevel;

    /**
     * 是否自动部署 0否 1 是
     */
    private Integer isAuto;

    /**
     * 是否完全部署
     */
    private Integer isFullyDeploy;

    /**
     * 发布补丁人员
     */
    private String pUid;

    /**
     * 补丁处理人员
     */
    private String dUid;

    /**
     * 补丁发布核实责任人员
     */
    private String vUid;

    /**
     * 补丁路径
     */
    private String detail;

    public void setpUid(String pUid) {
        this.pUid = pUid;
    }

    public void setdUid(String dUid) {
        this.dUid = dUid;
    }

    public void setvUid(String vUid) {
        this.vUid = vUid;
    }
}
