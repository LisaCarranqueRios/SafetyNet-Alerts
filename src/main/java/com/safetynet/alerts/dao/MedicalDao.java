package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.Medical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface contains method to manage medical data
 */
@Repository
public interface MedicalDao extends JpaRepository<Medical, Integer> {

    public Medical findById(int id);

    void deleteById(int id);

}
