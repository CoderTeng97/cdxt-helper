package com.cdxt.backend.pojo.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdxt.backend.model.IssuesOpLog;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class IssuesViewVO {

    /**
     * 问题id
     */
    private String id;
    /**
     * 问题标题
     */
    private String title;


    private String hospitalName;

    /**
     * 分类
     */
    private String classification;

    /**
     * 处理用户
     */
    private String dUser;

    /**
     * 校验用户
     */
    private String vUser;

    /**
     * 发布用户
     */
    private String pUser;
    /**
     * 状态
     */
    private String state;

    /**
     * 记录总条数
     */
    @JsonIgnore(value = false)
    private  Integer recordsTotal;

}
