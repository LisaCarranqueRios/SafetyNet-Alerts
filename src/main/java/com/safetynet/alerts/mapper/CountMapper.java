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
public class CountMapper {

    private List<PersonMapper> personsAtFirestation;
    private int adultCount;
    private int childCount;

}
