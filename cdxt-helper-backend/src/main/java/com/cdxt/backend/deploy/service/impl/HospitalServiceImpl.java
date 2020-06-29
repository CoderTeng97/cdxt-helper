package com.cdxt.backend.deploy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdxt.backend.deploy.model.Hospital;
import com.cdxt.backend.deploy.dao.HospitalMapper;
import com.cdxt.backend.deploy.pojo.dto.HospitalUpdateDTO;
import com.cdxt.backend.deploy.service.HospitalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public IPage<Hospital> getHospitalList(String text, Integer pageNum, Integer pageSize) {
        QueryWrapper<Hospital> wrapper =new QueryWrapper();
        if (text != null){
            wrapper.like(true,"name",text);
        }
        return this.page(new Page<>(pageNum,pageSize),wrapper);
    }

    @Override
    public Boolean updateHospital(HospitalUpdateDTO dto) {
        Hospital hospital = new Hospital();
        hospital.setId(dto.getId());
        hospital.setBranch(dto.getBranch());
        hospital.setName(dto.getName());
        return updateById(hospital);
    }
}
