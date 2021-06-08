package com.safetynet.alerts.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChildMapper {

    List<PersonMapper> children;
    List<PersonMapper> houseMembers;

}
