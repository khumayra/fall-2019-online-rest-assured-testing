package com.automation.tests.day4;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class ORDSTTestDay4_2 {
@BeforeAll
public static void setup() {
    baseURI = ConfigurationReader.getProperty("ORDS.URI");
}

    /*
    Warmup!
    given accept type is JSON
    When user sends a GET request to "/employees"
    Then status code is 200
    And Content type is application/json
    And response time is less than 3 seconds
     */

    @Test
    @DisplayName("Verify status code, content type and response time")
    public void employeeTest1(){
        given().
                contentType(ContentType.JSON).
        when().
                get("/employees").prettyPeek().
        then().
                statusCode(200).
                contentType(ContentType.JSON).
                time(lessThan(3L), TimeUnit.SECONDS);
    }

    /*
    Given accept type is JSON
    and parameters: q= {"country_id": "US"}
    When users sends a GET request to "/countries"
    Then status code is 200
    And Content type is application/json
    And country_name from payload is "United States of America"
     */

    @Test
    @DisplayName("Verify country name, content type and status code for country with ID US")
    public void verifyCountriesTest1() {
        given().accept(ContentType.JSON).queryParam("q", "{\"country_id\": \"US\"}").
                when().get("/countries").prettyPeek().
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                body("items[0].country_name", is("United States of America"));

        //SECOND REQUEST
        //accept (ContentType.JSON). - to request JSON from the web-service.
        Response response = given().
                accept(ContentType.JSON).
                when().
                get("/countries").prettyPeek();

        String countryName = response.jsonPath().get("items.find{it.country_id=='US'}.country_name");
        Map<String, Object> countryUS = response.jsonPath().get("items.find{it.country_id == 'US'}");
        //find all country names from region 2
        //collectionName.findAll{it.propertyName=='Value'} -- to get collection objects where property equals to some value
        //collectionName.find{it.propertyName=='Value'} -- to object where property equals to some value
        //collectionName.findAll{it.propertyName=='Value'}.propertyName --
        //to get collection properties where property equals to some value

        List<String> countryNames = response.jsonPath().getList("items.findAll{it.region_id==2}.country_name");

        System.out.println("Country name: " + countryName);
        System.out.println(countryUS);

        for (Map.Entry<String, Object> entry : countryUS.entrySet()) {
            System.out.printf("key = %s, value = %s\n", entry.getKey(), entry.getValue());
        }
    }
        //let's find employee with highest salary. USE GPATH
        @Test
        public void getEmployeeTest(){

            Response response = when().get("/employees").prettyPeek();

            Map<String, ?> bestEmployee = response.jsonPath().get("items.max{it.salary}");
            Map<String, ?> poorGuy = response.jsonPath().get("items.min{it.salary}");

            int companysPayroll = response.jsonPath().get("items.collect{it.salary}.sum()");

            System.out.println(bestEmployee);
            System.out.println(poorGuy);
        }

    /**
     * given path parameter is "/employees"
     * when user makes get request
     * then assert that status code is 200
     * Then user verifies that every employee has positive salary
     */
    @Test
    @DisplayName("")
    public void testSalary(){
        when().
                get("/employees").
        then()
                .assertThat().statusCode(200).
                body("items.salary", everyItem(greaterThan(0))).
                log().ifError();

    }

    @Test
    public void verifyPhoneNumber(){
        Response response = when().
                                get("/employees/{id}", 101).prettyPeek();

        String expected = "515-123-4568";
        String actual = response.jsonPath().getString("phone_number").replace(".", "-");

        assertEquals(200, response.statusCode());
        assertEquals(expected,actual);

    }


}



