package com.safetynet.alerts.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer age;
    private String email;
    private String address;
    private String city;
    private String zip;
    private Integer station;
    @OneToOne
    @JoinColumn(name = "medical_id")
    private Medical medical;
    @ManyToOne
    @JoinColumn(name ="firestation_id")
    private Firestation firestation;


}
