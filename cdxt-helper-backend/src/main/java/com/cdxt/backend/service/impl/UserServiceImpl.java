package com.cdxt.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cdxt.backend.dao.UserMapper;
import com.cdxt.backend.model.User;
import com.cdxt.backend.pojo.dto.UserLoginDTO;
import com.cdxt.backend.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdxt.common.exception.ResponseCommonException;
import com.cdxt.common.pojo.vo.UserBaseVO;
import com.cdxt.common.utils.IdWorker;
import com.cdxt.common.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Value("${pwd.secret}")
    private String secret;
    @Autowired
    IdWorker idWorker;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    BCryptPasswordEncoder bcryptPasswordEncoder;

    @Override
    public Boolean increaseUser(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        int count = baseMapper.selectCount(queryWrapper);

        if (count > 0){
            throw  new ResponseCommonException(HttpStatus.BAD_REQUEST,"用户名已存在,请换一个用户名称");
        }
       user.setId(idWorker.nextId() + "");
       user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()+ secret));
       return this.save(user);
    }

    @Override
    public UserBaseVO login(UserLoginDTO userLoginDTO) {
        UserBaseVO userBaseVO = baseMapper.selectByUserLoginDTO(userLoginDTO);
        if (userBaseVO == null){
           throw  new ResponseCommonException(HttpStatus.NOT_FOUND,"用户不存在");
        }

//        String hashPass = bcryptPasswordEncoder.encode(userLoginDTO.getPassword() + secret);
//        boolean isMatche = bcryptPasswordEncoder.matches(userBaseVO.getPassword(),hashPass);
//        if (false == isMatche){
//            throw  new ResponseCommonException(HttpStatus.NOT_FOUND,"用户或密码不正确");
//        }
        userBaseVO.setPassword(null);
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
        wrapper.eq(StringUtils.isNotEmpty(role),"role",role)
                .likeLeft(StringUtils.isNotEmpty(trueName),"true_name",trueName)
                .select("id","true_name");
       return  baseMapper.selectMaps(wrapper);
    }

    @Override
    public String getUserEmail(String uid) {
        return baseMapper.selectEmailById(uid);
    }

    @Override
    public String getTrueName(String uid) {
      return   baseMapper.selectTrueNameById(uid);
    }

    @Override
    public Boolean isExistUser(String uid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",uid);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count > 0 ?true : false;
    }

    @Override
    public Boolean resetPassword(String oldPassword, String newPassword, String uid) {
        if (false == verifyUserPassword(uid,oldPassword)){
            throw  new ResponseCommonException(HttpStatus.BAD_REQUEST,"旧密码错误,请确认后重试");
        }
        User user = new User();
        user.setPassword(newPassword + secret);
        user.setId(uid);
        return  this.updateById(user);
    }

    /**
     * 校验用户密码 一般用于登录后进行重要操作验证
     * @param uid
     * @param password
     * @return
     */
    Boolean verifyUserPassword(String uid,String password){
        String dbPassword =  baseMapper.selectPasswordById(uid);
        String hashPass = bcryptPasswordEncoder.encode(dbPassword + secret);
        boolean isMatche = bcryptPasswordEncoder.matches(password,hashPass);
       return  isMatche;
    }
}
