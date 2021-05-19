package com.safetynet.alerts.alerts;

import com.safetynet.alerts.controller.AlertController;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {"com.safetynet.alerts"})
@EnableAutoConfiguration
@SpringBootApplication
@EntityScan(basePackageClasses = {Person.class, Medical.class, Firestation.class})
@EnableJpaRepositories(basePackageClasses = {PersonDao.class})
public class Alerts  {


    public static void main(String[] args) {
        SpringApplication.run(Alerts.class, args);
    }

}
