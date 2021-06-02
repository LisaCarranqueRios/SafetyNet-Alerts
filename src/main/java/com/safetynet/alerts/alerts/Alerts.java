package com.safetynet.alerts.alerts;

import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Log4j2
@Configuration
@ComponentScan(basePackages = {"com.safetynet.alerts"})
@EnableAutoConfiguration
@SpringBootApplication
@EntityScan(basePackageClasses = {Person.class, Medical.class, Firestation.class})
@EnableJpaRepositories(basePackageClasses = {PersonDao.class})
public class Alerts  {

/*    @Bean
    public HttpTraceRepository htttpTraceRepository()
    {
        return new InMemoryHttpTraceRepository();
    }*/

    public static void main(String[] args) {
        log.info("Initializing Alerts System");
        SpringApplication.run(Alerts.class, args);
    }

}
