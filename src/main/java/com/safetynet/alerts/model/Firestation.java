package com.safetynet.alerts.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="firestation")
public class Firestation {

    @Id
    @GeneratedValue
    private int id;
    @Column(name="address")
    @Length(max=25)
    private String address;
    @Column(name="station")
    @Length(max=25)
    private int station;

}
