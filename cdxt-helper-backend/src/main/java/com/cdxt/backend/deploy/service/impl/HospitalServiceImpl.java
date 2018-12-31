package com.cdxt.backend.deploy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdxt.backend.deploy.model.Hospital;
import com.cdxt.backend.deploy.dao.HospitalMapper;
import com.cdxt.backend.deploy.pojo.dto.HospitalUpdateDTO;
import com.cdxt.backend.deploy.service.HospitalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cdxt.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

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
        }else{
            hospital.setId(dto.getId());
        }
        hospital.setBranch(dto.getBranch());
        hospital.setName(dto.getName());
        return this.saveOrUpdate(hospital);
    }
}
