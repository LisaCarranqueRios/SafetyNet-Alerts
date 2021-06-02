package com.safetynet.alerts.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

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
    @Length(max=25)
    private String lastName;
    @Column(name="fist_name")
    @Length(max=25)
    private String firstName;
    @Column(name="birthdate")
    @Length(max=25)
    private String birthdate;
    @ElementCollection
    @Column(name="medications")
    private List<String> medications;
    @ElementCollection
    @Column(name="allergies")
    private List<String> allergies;
}
