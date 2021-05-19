package com.safetynet.alerts.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonMapper {

    private String firstName;
    private String lastName;
    private String phone;
    private Integer age;
    private String email;
    private String address;
    private MedicalRecordMapper medicalRecord;
    private String station;

}
