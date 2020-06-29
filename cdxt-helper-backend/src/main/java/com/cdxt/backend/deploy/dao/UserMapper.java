package com.cdxt.backend.deploy.dao;

import com.cdxt.backend.deploy.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdxt.backend.deploy.pojo.dto.UserLoginDTO;
import com.cdxt.common.pojo.vo.UserBaseVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author TengGang
 * @since 2020-05-27
 */
public interface UserMapper extends BaseMapper<User> {

   UserBaseVO selectByUserLoginDTO(UserLoginDTO dto);

   @Select("select EMAIL from  tb_user where id=#{id}")
   String selectEmailById(@Param("id") String id);
}
