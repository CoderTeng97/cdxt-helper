package com.cdxt.backend.deploy.pojo.dto;

import lombok.Data;

/*
 * @Author: TengGang
 * @Date: 2020/6/7 21:53
 * @Description:  医院分支更新
 */
@Data
public class HospitalUpdateDTO {

    private String id;
    /**
     * 医院名称
     */
    private String name;

    /**
     * 对应分支名称
     */
    private String branch;

}
