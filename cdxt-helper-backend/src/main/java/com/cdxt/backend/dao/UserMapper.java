package com.cdxt.backend.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdxt.backend.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdxt.backend.pojo.dto.UserLoginDTO;
import com.cdxt.backend.pojo.vo.UserViewVO;
import com.cdxt.common.pojo.vo.UserBaseVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

   /**
    * 查询邮箱
    * @param id
    * @return
    */
   @Select("select EMAIL from  tb_user where id=#{id}")
   String selectEmailById(@Param("id") String id);

   /**
    * 查询真实姓名
    * @param id
    * @return
    */
   @Select("select true_name from  tb_user where id=#{id}")
   String selectTrueNameById(@Param("id") String id);

   /**
    * 查询密码
    * @param id
    * @return
    */
   @Select("select password from  tb_user where id=#{id}")
   String selectPasswordById(@Param("id") String id);


   @Select("select password from  tb_user where username=#{username}")
   String selectPasswordByUsername(@Param("username") String username);


   IPage<UserViewVO> selectViewPage(Page page,@Param("trueName") String trueName);
}
