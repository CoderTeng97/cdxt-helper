package com.cdxt.backend.deploy.pojo.vo;

import com.cdxt.backend.deploy.model.DeployPostSrc;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DeployViewVO {

    private String id;

    /**
     * 医院id
     */
    private String hospitalId;

    /**
     * 医院名称
     */
    private String hospitalName;
    /**
     * 分支名称
     */
    private String branchName;
    /**
     * 所属分支
     */
    private String branch;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;

    /**
     * 0 未处理 1 已处理
     */
    private Integer state;

    /**
     * 发布等级
     */
    private Integer postLevel;

    /**
     * 处理人名称
     */
    private String dUsername;

    /**
     * 发布人名称
     */
    private String pUsername;


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

    private List<DeploySrcViewVO> fileSrcList;

    public void setdUsername(String dUsername) {
        this.dUsername = dUsername;
    }

    public void setpUsername(String pUsername) {
        this.pUsername = pUsername;
    }

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
