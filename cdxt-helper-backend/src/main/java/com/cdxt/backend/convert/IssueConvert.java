package com.cdxt.backend.convert;

import com.cdxt.backend.model.Issues;
import com.cdxt.backend.pojo.dto.IssuesPostDTO;
import com.cdxt.backend.pojo.dto.IssuesQueryDTO;
import com.cdxt.backend.pojo.dto.IssuesUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
@Component
public interface  IssueConvert {
    @Mappings({
            @Mapping(target ="classification" ,ignore = true),
            @Mapping(target ="gmtCompletion" ,ignore = true),
            @Mapping(target ="gmtModified" ,ignore = true),
            @Mapping(target ="gmtCreate" ,ignore = true),
            @Mapping(target ="isDel" ,ignore = true),
            @Mapping(target ="id" ,ignore = true)

    })
    Issues postParams2Entity (IssuesPostDTO issuesPostDTO);
    Issues updateParams2Entity(IssuesUpdateDTO issuesUpdateDTO);
    Issues queryParams2Entity(IssuesQueryDTO issuesQueryDTO);
}
