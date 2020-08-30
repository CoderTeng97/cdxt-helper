package com.cdxt.backend.enums;

/**
 * 售后管理wss消息类型
 */
public enum OperationTypeEnum {
    UPDATE_ISSUE_0("退回问题任务"),
    UPDATE_ISSUE_1("开始问题任务"),
    UPDATE_ISSUE_2("问题任务完成进行审核中"),
    UPDATE_ISSUE_3("问题处理完成");


    private String name;
    OperationTypeEnum(String name){
        this.name = name;
    }
}
