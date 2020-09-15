package com.cdxt.backend.dao;

import com.cdxt.backend.model.Hospital;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author TengGang
 * @since 2020-05-27
 */
public interface HospitalMapper extends BaseMapper<Hospital> {
    @Select("select count(1) from tb_hospital where name=#{name}")
    Integer selectCountWithName(String name);
}
