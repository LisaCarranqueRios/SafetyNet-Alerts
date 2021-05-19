package com.safetynet.alerts.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Firestation {

    @Id
    @GeneratedValue
    private int id;
    private String address;
    private int station;

}
