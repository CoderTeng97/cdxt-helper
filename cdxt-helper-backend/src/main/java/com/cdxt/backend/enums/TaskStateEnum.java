package com.cdxt.backend.enums;

/**
 *
 */
public enum TaskStateEnum {

    UNPROCESSED("未处理","0"),
    PROCESSING("处理中","1"),
    AUDIT("审核中","2"),
    RESOLVE ("处理完成","3");



    private String label;
    private String value;
    TaskStateEnum(String label, String value){
        this.label = label;
        this.value = value;
    }

    public String label(){
        return this.label;
    }

    public String value(){
        return this.value;
    }
}
