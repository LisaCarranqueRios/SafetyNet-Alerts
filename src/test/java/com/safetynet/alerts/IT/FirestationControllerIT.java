package com.safetynet.alerts.IT;

import com.safetynet.alerts.controller.AlertController;
import com.safetynet.alerts.controller.FirestationController;
import com.safetynet.alerts.controller.MedicalController;
import com.safetynet.alerts.controller.PersonController;
import com.safetynet.alerts.dao.FirestationDao;
import com.safetynet.alerts.dao.MedicalDao;
import com.safetynet.alerts.dao.PersonDao;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.Medical;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.*;
import com.safetynet.alerts.utils.AlertsUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {AlertController.class, FirestationController.class,
        MedicalController.class, PersonController.class,
        IMedicalService.class, MedicalService.class,
        IPersonService.class, PersonService.class,
        IFirestationService.class, FirestationService.class,
        IAlertsService.class, AlertsService.class, AlertsUtils.class})
@AutoConfigureMockMvc
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = {MedicalDao.class, PersonDao.class, FirestationDao.class})
@EntityScan(basePackageClasses = {Medical.class, Firestation.class, Person.class})
public class FirestationControllerIT {

    @Autowired
    private MockMvc mockMvc;
    /*
    Firestation
     */
    @Test
    public void testListFirestation() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/firestations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("application/json",
                mvcResult.getResponse().getContentType());
    }

    @Test
    public void testDisplayFirestation() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/firestations/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testAddFirestation() throws Exception {
        String jsonbody = "{ \"address\":\"1509 Culver St\", \"station\":\"30\" }";
        MvcResult mvcResult = this.mockMvc.perform(post("/firestation").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void testAddFirestations() throws Exception {
        String jsonbody = " [{ \"address\":\"1509 Culver St\", \"station\":\"3\" },\n" +
                "    { \"address\":\"29 15th St\", \"station\":\"2\" }]";
        MvcResult mvcResult = this.mockMvc.perform(post("/persons").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testDeleteFirestation() throws Exception {
        String jsonbody = "{ \"id\":\"100\", \"address\":\"1509 Culver St\", \"station\":\"30\" }";
        MvcResult mvcResult = this.mockMvc.perform(post("/firestation").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String savedLocation = mvcResult.getResponse().getHeaders("Location").get(0).toString();
        String id = savedLocation.replaceAll("[^0-9]", "");
        MvcResult mvcResult2 = this.mockMvc.perform(delete("/firestations/{id}", id).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void testUpdateFirestation() throws Exception {
        String jsonbody = "{ \"address\":\"1509 Culver St\", \"station\":\"30\" }";
        MvcResult mvcResult = this.mockMvc.perform(put("/firestations").content(jsonbody).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }


}
