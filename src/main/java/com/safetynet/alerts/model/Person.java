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
@Table(name="person")
public class Person {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="phone")
    private String phone;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name="age")
    private Integer age;
    @Column(name="email")
    private String email;
    @Column(name="address")
    private String address;
    @Column(name="city")
    private String city;
    @Column(name="zip")
    private String zip;
    @Column(name="station")
    private Integer station;
    @OneToOne
    @JoinColumn(name = "medical_id")
    private Medical medical;
    @ManyToOne
    @JoinColumn(name ="firestation_id")
    private Firestation firestation;


}
