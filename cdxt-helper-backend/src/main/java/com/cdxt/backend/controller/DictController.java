package com.cdxt.backend.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cdxt.backend.model.Dict;
import com.cdxt.backend.model.DictD;
import com.cdxt.backend.service.DictDService;
import com.cdxt.backend.service.DictService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;

@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    DictService dictService;
    @Autowired
    DictDService dictDService;


    @ApiOperation("字典新增")
    @PostMapping("/release")
    Boolean releaseDict(Dict dict){
       return dictService.save(dict);
    }

    @ApiOperation("字典更新")
    @PostMapping("/item/release")
    Boolean releaseDictItem(DictD  dictD){
        return dictDService.save(dictD);
    }

    @ApiOperation("字典项新增")
    @PostMapping("/item/update")
    Boolean updateDictItem(DictD  dictD){
        return dictDService.updateById(dictD);
    }
    @ApiOperation("字典项新增")
    @PostMapping("/update")
    Boolean updateDict(Dict dict){
        return dictService.updateById(dict);
    }

    @DeleteMapping("/item/del/{dictId}/{label}")
    Boolean deleteDictItem(@PathVariable String dictId,String label){
        UpdateWrapper<DictD> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",dictId);
        wrapper.eq("label",label);
        return dictDService.remove(wrapper);
    }




}
