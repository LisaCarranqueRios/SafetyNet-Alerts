package com.safetynet.alerts.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class Medical {

    @Id
    @GeneratedValue
    private int id;
    private String lastName;
    private String firstName;
    private String birthdate;
    @ElementCollection
    @Column(name="medications")
    private List<String> medications;
    @ElementCollection
    @Column(name="allergies")
    private List<String> allergies;
}
