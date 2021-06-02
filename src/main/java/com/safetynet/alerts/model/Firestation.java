package com.safetynet.alerts.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table
public class Firestation {

    @Id
    @GeneratedValue
    private int id;
    private String address;
    private int station;

}
