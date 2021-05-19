package com.safetynet.alerts.dao;

import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface MedicalDao extends JpaRepository<Medical, Integer> {
    public List<Medical> findAll();
    public Medical findById(int id);
    public Medical save(Medical medical);

    void deleteById(int id);

}