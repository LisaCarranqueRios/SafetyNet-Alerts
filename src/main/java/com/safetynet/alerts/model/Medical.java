package com.safetynet.alerts.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="medical")
public class Medical {

    @Id
    @GeneratedValue
    private int id;
    @Column(name="last_name")
    private String lastName;
    @Column(name="fist_name")
    private String firstName;
    @Column(name="birthdate")
    private String birthdate;
    @ElementCollection
    @Column(name="medications")
    private List<String> medications;
    @ElementCollection
    @Column(name="allergies")
    private List<String> allergies;
}
