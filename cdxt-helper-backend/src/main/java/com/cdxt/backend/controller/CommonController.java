package com.cdxt.backend.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdxt.backend.model.Dict;
import com.cdxt.backend.model.DictD;
import com.cdxt.backend.model.Hospital;
import com.cdxt.backend.pojo.dto.HospitalUpdateDTO;
import com.cdxt.backend.service.DictDService;
import com.cdxt.backend.service.DictService;
import com.cdxt.backend.service.HospitalService;
import com.cdxt.common.annotation.ControllerResponseProcessor;
import com.cdxt.common.pojo.vo.ResponseListVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/common")
@ControllerResponseProcessor
public class CommonController {

    @Autowired
    HospitalService hospitalService;
    @Autowired
    DictService dictService;
    @Autowired
    DictDService dictDService;

    @ApiOperation("获取医院列表")
    @GetMapping("/hospitals")
    public ResponseListVO<Hospital> hospitals(
            @RequestParam(required = false)
                    String text,
            @RequestParam(required = true,defaultValue = "1")
                    Integer pageNum,
            @RequestParam(required = true,defaultValue = "10")
                    Integer pageSize
    ){
        IPage<Hospital> iPage = hospitalService.getHospitalList(text,pageNum,pageSize);
        ResponseListVO<Hospital> vo = new ResponseListVO<>(iPage.getCurrent(),iPage.getSize(),iPage.getTotal(),iPage.getRecords());
        return vo;
    }


    @ApiOperation("更新或插入医院信息")
    @PostMapping("/savaOrUpdateHospital")
    public Boolean hospitals(
            @RequestBody HospitalUpdateDTO dto
    ){
        return hospitalService.saveOrUpdateHospital(dto);
    }




    @ApiOperation("字典新增")
    @PostMapping("/dict/release")
    Boolean releaseDict(Dict dict){
        return dictService.save(dict);
    }

    @ApiOperation("字典更新")
    @PostMapping("/dict/item/release")
    Boolean releaseDictItem(DictD dictD){
        return dictDService.save(dictD);
    }

    @ApiOperation("字典项新增")
    @PostMapping("/dict/item/update")
    Boolean updateDictItem(DictD  dictD){
        return dictDService.updateById(dictD);
    }
    @ApiOperation("字典项新增")
    @PostMapping("/dict/update")
    Boolean updateDict(Dict dict){
        return dictService.updateById(dict);
    }

    @DeleteMapping("/dict/item/del")
    Boolean deleteDictItem(@RequestParam String dictId,@RequestParam String label){
        UpdateWrapper<DictD> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",dictId);
        wrapper.eq("label",label);
        return dictDService.remove(wrapper);
    }

}
