package com.cdxt.backend.service;

import com.cdxt.backend.model.Issues;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cdxt.backend.pojo.dto.IssuesPostDTO;
import com.cdxt.backend.pojo.dto.IssuesQueryDTO;
import com.cdxt.backend.pojo.dto.IssuesUpdateDTO;
import com.cdxt.backend.pojo.vo.IssuesDetailVO;
import com.cdxt.backend.pojo.vo.IssuesViewVO;
import com.cdxt.common.pojo.vo.ResponseListVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author TengGang
 * @since 2020-08-25
 */
public interface IssuesService extends IService<Issues> {

    /**
     * 发布问题
     * @param postDTO
     * @return
     */
    public Boolean releaseIssue(IssuesPostDTO postDTO);

    /**
     * 更新问题状态
     * @param dto 更新参数
     * @param opUid 操作人员
     * @return
     */
    public Boolean updateIssueState(IssuesUpdateDTO dto,String opUid);

    /**
     * 搜索问题列表
     * @param dto
     * @return
     */
    public ResponseListVO<IssuesViewVO> searchIssueList(IssuesQueryDTO dto)  ;

    /**
     * 问题详情
     * @param issueId
     * @return
     */
    public IssuesDetailVO issueDetail(String issueId);
    /**
     * 指派用户处理问题
     * @param issueId 问题id
     * @param dUid
     * @return
     */
    Boolean assignIssueDealUser(String issueId,String dUid,String opUid,String feedbackText);
}
