package com.cdxt.backend.convert;

import com.cdxt.backend.model.User;
import com.cdxt.backend.pojo.dto.UserUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserConvert {


    @Mappings({
            @Mapping(target ="gmtModified" ,ignore = true),
            @Mapping(target ="gmtCreate" ,ignore = true),
            @Mapping(target ="password" ,ignore = true),
    })
    User updateParams2Entity(UserUpdateDTO userUpdateDTO);
}
