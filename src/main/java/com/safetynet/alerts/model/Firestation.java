package com.safetynet.alerts.model;

import lombok.*;

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
    private String address;
    @Column(name="station")
    private int station;

}
