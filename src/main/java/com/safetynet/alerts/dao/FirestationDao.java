package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface FirestationDao extends JpaRepository<Firestation, Integer> {

    public List<Firestation> findAll();
    public Firestation findById(int id);
    public Firestation save(Firestation firestation);

    void deleteById(int id);

    @Query("select f FROM Firestation f WHERE f.station=:station")
    List<Firestation> findByStation(int station);

    @Query("select f FROM Firestation f WHERE f.address=:address")
    List<Firestation> findByAddress(String address);

}