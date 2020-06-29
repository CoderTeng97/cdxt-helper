package com.cdxt.common.enums;

/**
 * @Author: TengGang
 * @Date: 2019/7/7 14:05
 * @Description:
 */
public enum ResponseMsgType {
    OK("操作成功！"),
    FAIL("亲,操作失败啦~"),
    NOT_FOUND("亲,目前没有您要找的信息啦~"),
    INTERNAL_SERVER_ERROR("亲,系统开了一点小差,请稍后再试~")
    ;

    private String msg;

    ResponseMsgType(String msg){
        this.msg = msg;
    }

    public String msg(){
        return this.msg;
    }
}
