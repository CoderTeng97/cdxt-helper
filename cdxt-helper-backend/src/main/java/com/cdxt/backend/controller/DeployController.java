package com.cdxt.backend.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdxt.backend.model.Hospital;
import com.cdxt.backend.pojo.dto.DeployPostDTO;
import com.cdxt.backend.pojo.dto.DeployPostQueryDTO;
import com.cdxt.backend.pojo.dto.HospitalUpdateDTO;
import com.cdxt.backend.pojo.vo.DeployViewVO;
import com.cdxt.backend.service.DeployPostService;
import com.cdxt.backend.service.HospitalService;
import com.cdxt.common.annotation.ControllerResponseIgnore;
import com.cdxt.common.annotation.ControllerResponseProcessor;
import com.cdxt.common.base.BaseController;
import com.cdxt.common.enums.UserRole;
import com.cdxt.common.exception.ResponseCommonException;
import com.cdxt.common.pojo.vo.ResponseListVO;
import com.cdxt.common.pojo.vo.UserBaseVO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


@Slf4j
@RestController
@RequestMapping("/deploy")
@ControllerResponseProcessor
public class DeployController extends BaseController {
    @Autowired
    DeployPostService deployPostService;


    @ApiOperation("发布补丁")
    @PutMapping("/post")
    public Boolean post(@RequestBody DeployPostDTO deployPostDTO){
      deployPostDTO.setpUid(getUid());
      return   deployPostService.increaseDeployPost(deployPostDTO);
    }

    @ApiOperation("获取部署列表")
    @PostMapping("/list")
    public  ResponseListVO  list(@RequestBody DeployPostQueryDTO deployPostQueryDTO){
        UserBaseVO userBaseVO = getUserInfo();
        UserRole userRole = UserRole.valueOf(userBaseVO.getRole().toUpperCase());
        if (UserRole.ADMIN.equals(userRole)|| UserRole.DEPLOYER.equals(userRole)){
            //TODO
        }else{
            deployPostQueryDTO.setUid(userBaseVO.getId());
        }
        deployPostQueryDTO.setUserRole(userRole.name());
        IPage<DeployViewVO> iPage = deployPostService.getDeployViewList(deployPostQueryDTO);
        ResponseListVO<DeployViewVO> vo = new ResponseListVO<>(iPage.getCurrent(),iPage.getSize(),iPage.getTotal(),iPage.getRecords());
        return vo ;
    }

    @ApiOperation("修改状态")
    @PostMapping("/update/state")
    public Boolean updateState(@RequestParam(required = true) String did,@RequestParam(required = true) Integer state){
       return deployPostService.udpateDeployState(did,state);
    }



    @ControllerResponseIgnore
    @ApiOperation("下载补丁")
    @GetMapping("/patches/download")
    public void patchesDownload(@ApiParam(value = "补丁id", required = true) @RequestParam String patchId, HttpServletResponse response){
        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition", "attachment;fileName=" + patchId+".zip");// 设置文件名
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            deployPostService.getDeployPatchesWithZip(patchId,response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            log.error("补丁下载发生IO异常",e);
            throw  new ResponseCommonException(HttpStatus.INTERNAL_SERVER_ERROR,"补丁下载发生IO异常");
        }catch (Exception e){
            e.printStackTrace();
            log.error("补丁下载发生异常",e);
            throw  new ResponseCommonException(HttpStatus.INTERNAL_SERVER_ERROR,"补丁下载发生IO异常");
        }
    }


    @ApiOperation("设置处理人员")
    @GetMapping("/setDUser")
    public Boolean setDUser(
            @RequestParam
            String duid,
            @RequestParam
            String deployPostId

    ){
      return   deployPostService.setDUser(deployPostId,duid);
    }


    @ApiOperation("修改状态")
    @PostMapping("/del")
    public Boolean del(@RequestParam String id){
        return deployPostService.delPost(id);
    }


}
