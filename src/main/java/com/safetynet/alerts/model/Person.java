package com.safetynet.alerts.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.validator.constraints.Length;

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
    @Length(max=25)
    private String firstName;
    @Column(name="last_name")
    @Length(max=25)
    private String lastName;
    @Column(name="phone")
    @Length(max=25)
    private String phone;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name="age")
    private Integer age;
    @Column(name="email")
    @Length(max=25)
    private String email;
    @Column(name="address")
    @Length(max=25)
    private String address;
    @Column(name="city")
    @Length(max=25)
    private String city;
    @Column(name="zip")
    @Length(max=25)
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
