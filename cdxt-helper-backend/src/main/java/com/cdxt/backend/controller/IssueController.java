package com.cdxt.backend.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdxt.backend.model.IssuesOpLog;
import com.cdxt.backend.pojo.dto.IssuesPostDTO;
import com.cdxt.backend.pojo.dto.IssuesQueryDTO;
import com.cdxt.backend.pojo.dto.IssuesUpdateDTO;
import com.cdxt.backend.pojo.vo.IssuesDetailVO;
import com.cdxt.backend.pojo.vo.IssuesViewVO;
import com.cdxt.backend.service.IssuesOpLogService;
import com.cdxt.backend.service.IssuesService;
import com.cdxt.backend.websockt.AfterSafeIssueWebsocket;
import com.cdxt.common.annotation.ControllerResponseProcessor;
import com.cdxt.common.base.BaseController;
import com.cdxt.common.pojo.vo.ResponseListVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@ControllerResponseProcessor
@RequestMapping("/after-safe/issue")
public class IssueController extends BaseController {

    @Autowired
    IssuesService issuesService;
    @Autowired
    IssuesOpLogService issuesOpLogService;
    @Autowired
    AfterSafeIssueWebsocket afterSafeIssueWebsocket;


    @GetMapping("/sendWssMessage")
    public void test(){
        JSONObject message = new JSONObject();
        message.put("type","notice");
        message.put("data","这是一个测试");
        try {
            afterSafeIssueWebsocket.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("发布问题任务")
    @PutMapping("/release")
    public Boolean releaseIssue(@RequestBody IssuesPostDTO  dto){
        dto.setpUid(getUid());
        return issuesService.releaseIssue(dto);
    }
    @ApiOperation("更新问题处理状态")
    @PostMapping("/updateState")
    public Boolean updateState(IssuesUpdateDTO dto){
        return issuesService.updateIssueState(dto,getUid());
    }

    @ApiOperation("查询问题列表")
    @PostMapping("/list")
    public ResponseListVO<IssuesViewVO> searchIssueList(IssuesQueryDTO dto){
        return issuesService.searchIssueList(dto);
    }

    @ApiOperation("根据问题Id查询详情")
    @GetMapping("/detail/{issueId}")
    public IssuesDetailVO issueDetail(@PathVariable String issueId){
        return issuesService.issueDetail(issueId);
    }


    @ApiOperation("获取所有问题操作历史日志记录")
    @GetMapping("/history-log")
    public ResponseListVO<IssuesOpLog> getIssueLogList(
            @RequestParam(required = false,defaultValue = "1") Long pageNum,
            @RequestParam(required = false,defaultValue = "10") Long pageSize
    ){
        IPage<IssuesOpLog> iPage =  issuesOpLogService.getHistoryIssuesOpLog(pageNum,pageSize);
        ResponseListVO<IssuesOpLog> vo = new ResponseListVO<>(iPage.getCurrent(),iPage.getSize(),iPage.getTotal(),iPage.getRecords());
        return vo;
    }



    /**
     * 指派用户
     * @param issueId 问题id
     * @param dUid
     * @return
     */
    @ApiOperation("指派问题任务给用户")
    @GetMapping("/assignUser")
    public Boolean updateState(
                            @RequestParam String issueId,
                            @RequestParam String dUid){
        return issuesService.assignIssueDealUser(issueId,dUid,getUid());
    }


}
