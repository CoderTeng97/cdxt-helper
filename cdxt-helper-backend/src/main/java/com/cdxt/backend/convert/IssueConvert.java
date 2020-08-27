package com.cdxt.backend.convert;

import com.cdxt.backend.model.Issues;
import com.cdxt.backend.pojo.dto.IssuesPostDTO;
import com.cdxt.backend.pojo.dto.IssuesQueryDTO;
import com.cdxt.backend.pojo.dto.IssuesUpdateDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface  IssueConvert {
    Issues postParams2Entity (IssuesPostDTO issuesPostDTO);
    Issues updateParams2Entity(IssuesUpdateDTO issuesUpdateDTO);
    Issues queryParams2Entity(IssuesQueryDTO issuesQueryDTO);
}
