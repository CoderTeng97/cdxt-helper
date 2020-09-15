package com.cdxt.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdxt.backend.dao.HospitalMapper;
import com.cdxt.backend.model.Hospital;
import com.cdxt.backend.pojo.dto.HospitalUpdateDTO;
import com.cdxt.backend.service.HospitalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdxt.common.exception.ResponseCommonException;
import com.cdxt.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author TengGang
 * @since 2020-05-27
 */
@Service
public class HospitalServiceImpl extends ServiceImpl<HospitalMapper, Hospital> implements HospitalService {
    @Autowired
    IdWorker idWorker;

    @Override
    public IPage<Hospital> getHospitalList(String text, Integer pageNum, Integer pageSize) {
        QueryWrapper<Hospital> wrapper =new QueryWrapper();
        if (text != null){
            wrapper.like(true,"name",text);
        }
        return this.page(new Page<>(pageNum,pageSize),wrapper);
    }

    @Override
    public Boolean saveOrUpdateHospital(HospitalUpdateDTO dto) {
        Hospital hospital = new Hospital();
        if (StringUtils.isEmpty(dto.getId())){
            hospital.setId(idWorker.nextId()+"");
            //判断医院是否存在
            Integer count =  baseMapper.selectCountWithName(dto.getName());
            if (count > 0 ){
                throw  new ResponseCommonException("医院信息已存在,请勿重新添加");
            }

        }else{
            hospital.setId(dto.getId());
        }
        hospital.setBranch(dto.getBranch());
        hospital.setName(dto.getName());
        return this.saveOrUpdate(hospital);
    }
}
