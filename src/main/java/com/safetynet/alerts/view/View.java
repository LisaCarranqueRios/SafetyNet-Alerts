package com.safetynet.alerts.view;

import org.springframework.stereotype.Component;


@Component
public class View {


/*

    */
/**
     * This method is responsible for giving a list of persons covered by a firestation
     * @param personCoveredByFirestation
     *//*

    public void getPersonCoveredByFirestation(List<JSONObject> personCoveredByFirestation) {
        for (JSONObject person : personCoveredByFirestation) {
            System.out.println((person.get("lastName").toString() +
                    " " +person.get("firstName").toString() + " " +person.get("address").toString() + " " +person.get("phone").toString()));
        }
    }

    */
/**
     * This method is responsible for giving a list of children at address, with their age and their family members
     * @param childrenAtAddress
     *//*

    public void getChildrenAtAddress(List<JSONObject> childrenAtAddress) {
*/
/*        for (JSONObject person : childrenAtAddress) {
            System.out.println((person.get("lastName").toString() + " " +person.get("firstName").toString() +
                    " " + person.get("address").toString() + " " + person.get("phone").toString()));
        }*//*

    }

    */
/**
     * This method is responsible for giving a list of person covered by a firestation and their phone number
     * @param personCovered
     *//*

    public void getPersonCoveredByFirestationPhoneNumber(List<JSONObject> personCovered) {
        for (JSONObject person : personCovered) {
            System.out.println((person.get("lastName").toString()
                    + " " +person.get("firstName").toString() + " "  + person.get("phone").toString()));
        }
    }

    */
/**
     * This method is responsible for giving a list of person living at a targeted address with their firestation coverage
     * @param personWithFirestationCoverage
     *//*

    public void getPersonWithFirestationCoverage(HashMap<List<Person>, Integer> personWithFirestationCoverage) {
        for (List<Person> persons : personWithFirestationCoverage.keySet()) {
            for (Person person : persons) {
                System.out.println("Person at address" + person.getAddress());
            }
        }
    }

    */
/**
     * This method is responsible for giving a list of person at a targeted address with their medical records
     * @param personWithMedication
     *//*

    public void getPersonAtAddress(HashMap<JSONObject, List<JSONObject>> personWithMedication) {
        System.out.println(personWithMedication);
        int i = 1;
        for (JSONObject person : personWithMedication.keySet()) {
            System.out.println("Record number " + i + " " + person.get("lastName") + " "  + person.get("firstName"));
            i++;
            System.out.println(person);
            System.out.println(personWithMedication.get(person));
        }
    }

    */
/**
     * This method is responsible for giving a list of person with their personal information and their medical
     * @param personInformation
     *//*

    public void getPersonInformation(HashMap<JSONObject, List<JSONObject>> personInformation) {
        int i = 1;
        for (JSONObject person : personInformation.keySet()) {
            System.out.println("Record number " + i + " " + person.get("lastName") + " "  + person.get("firstName"));
            i++;
            System.out.println(person);
            System.out.println(personInformation.get(person));
        }
    }

    */
/**
     * This method is responsible for giving a list of email for all the inhabitants of the city
     * @param personEmail
     *//*

    public void getPersonEmail(List<JSONObject> personEmail) {
        for (JSONObject person : personEmail) {
            System.out.println((person.get("lastName").toString() + " " +person.get("firstName").toString()+ " " + person.get("email").toString()));
        }
    }
*/


}
