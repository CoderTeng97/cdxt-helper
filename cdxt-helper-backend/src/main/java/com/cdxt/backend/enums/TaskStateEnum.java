package com.cdxt.backend.enums;

/**
 *
 */
public enum TaskStateEnum {

    UNPROCESSED("未处理","1"),
    PROCESSING("处理中","1"),
    RESOLVE ("处理完成","1");


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
