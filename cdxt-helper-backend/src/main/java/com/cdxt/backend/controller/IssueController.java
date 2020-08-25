package com.cdxt.backend.controller;


import com.cdxt.common.annotation.ControllerResponseProcessor;
import com.cdxt.common.base.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerResponseProcessor
@RequestMapping("/after-safe/issue/")
public class IssueController extends BaseController {
    public Boolean releaseIssue(){
        return false;
    }
}
