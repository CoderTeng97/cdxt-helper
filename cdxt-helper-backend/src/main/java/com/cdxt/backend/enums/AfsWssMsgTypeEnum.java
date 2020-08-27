package com.cdxt.backend.enums;

/**
 * 售后管理wss消息类型
 */
public enum AfsWssMsgTypeEnum {
    ISSUE_OP_LOG("问题操作日志"),
    LOG("提示日志"),
    NOTICE("公告");


    private String name;
    AfsWssMsgTypeEnum(String name){
        this.name = name;
    }
}
