package com.cdxt.backend.pojo.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WatcherVO {

    private Integer id;
    /**
     * 值班人员姓名
     */
    private String trueName;
    
    /**
     * 值班人员用户id
     */
    private String uid;

    /**
     * 值班开始时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 值班结束时间
     */
    private LocalDateTime gmtEndTime;

    /**
     * 值班类型
     */
    private String type;

    /**
     * 负责模块
     */
    private String module;
}
