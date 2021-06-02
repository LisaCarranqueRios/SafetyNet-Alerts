package com.safetynet.alerts.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.safetynet.alerts.model.Medical;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
/**
 * This class is used to map person data output
 */
public class PersonMapper {

    private String firstName;
    private String lastName;
    private String phone;
    private Integer age;
    private String email;
    private String address;
    private Medical medical;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int station;
}
