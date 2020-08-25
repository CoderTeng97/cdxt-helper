package com.cdxt.backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdxt.backend.model.Hospital;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cdxt.backend.pojo.dto.HospitalUpdateDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TengGang
 * @since 2020-05-27
 */
public interface HospitalService extends IService<Hospital> {
   /**
    * 获取医院列表
    * @param text
    * @param pageNum
    * @param pageSize
    * @return
    */
   IPage<Hospital> getHospitalList(String text,Integer pageNum ,Integer pageSize);

   /**
    * 更新或保存医院信息
    * @param dto
    * @return
    */
   Boolean saveOrUpdateHospital(HospitalUpdateDTO dto);
}
