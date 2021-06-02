package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.Firestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface contains methods to manage firestation data
 */
@Repository
public interface FirestationDao extends JpaRepository<Firestation, Integer> {

    public Firestation findById(int id);

    void deleteById(int id);

    @Query("select f FROM Firestation f WHERE f.station=:station")
    List<Firestation> findByStation(int station);

    @Query("select f FROM Firestation f WHERE f.address=:address")
    List<Firestation> findByAddress(String address);

}
