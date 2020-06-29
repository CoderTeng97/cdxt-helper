package com.cdxt.backend.deploy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdxt.backend.deploy.model.User;
import com.cdxt.backend.deploy.dao.UserMapper;
import com.cdxt.backend.deploy.pojo.dto.UserLoginDTO;
import com.cdxt.backend.deploy.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdxt.common.exception.ResponseCommonException;
import com.cdxt.common.pojo.vo.UserBaseVO;
import com.cdxt.common.utils.IdWorker;
import com.cdxt.common.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TengGang
 * @since 2020-05-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    IdWorker idWorker;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public Boolean increaseUser(User user) {
       user.setId(idWorker.nextId() + "");
       return this.save(user);
    }

    @Override
    public UserBaseVO login(UserLoginDTO userLoginDTO) {
        UserBaseVO userBaseVO = baseMapper.selectByUserLoginDTO(userLoginDTO);
        if (userBaseVO == null){
           throw  new ResponseCommonException(HttpStatus.NOT_FOUND,"用户不存在");
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", userBaseVO.getId());
        claims.put("username", userBaseVO.getUsername());
        claims.put("role", userBaseVO.getRole());
        userBaseVO.setToken(jwtTokenUtil.generateToken(claims));
        return userBaseVO;
    }

    @Override
    public List<Map<String,Object>>  getUserList(String trueName,String role) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq(!StringUtils.isEmpty(trueName),"role",role.trim())
                .like(!StringUtils.isEmpty(role),"true_name",trueName)
                .select("id","true_name");
       return  baseMapper.selectMaps(wrapper);
    }

    @Override
    public String getUserEmail(String uid) {
        return baseMapper.selectEmailById(uid);
    }
}
