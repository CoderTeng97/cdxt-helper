package com.cdxt;


import com.cdxt.backend.CdxtHelperApplication;
import com.cdxt.backend.convert.IssueConvert;
import com.cdxt.backend.model.Issues;
import com.cdxt.backend.pojo.dto.IssuesPostDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CdxtHelperApplication.class)
public class MapStructTest {



    @Test
    public void covertEntity(){
        IssuesPostDTO dto = new IssuesPostDTO();
        dto.setTitle("123123123");
        dto.setDetail("123143123");
        IssueConvert issueConvert =  Mappers.getMapper(IssueConvert.class);
        Issues  issues= issueConvert.postParams2Entity(dto);
        System.out.println(issues);

    }
}
