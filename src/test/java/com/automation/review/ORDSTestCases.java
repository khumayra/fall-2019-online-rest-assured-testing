package com.automation.review;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;


public class ORDSTestCases {
    /*
    get all the records from the employees tables using the / employees
    verify that number of employees is more than 100
    need to identify number of object
     */

    /*
    get all the employees and their depart ids.
    verify that department id points to the existing record in the departments table
     */
    @BeforeAll
    public static void setup(){
        baseURI = "http://54.146.89.247:1000/ords/hr/";
    }

    @Test
    @DisplayName("Verify that number of employees is more than 100")
    public void checkQtyOfEmployees(){
        Response response = given().
                                    queryParam("limit", 110).
                            when().
                                    get("/employees");
        response.
                            then().statusCode(200);

        List<Map<String, Object>> employees = response.jsonPath().getList("items");
        System.out.println(employees.size());
        System.out.println(employees.get(1));
        assertThat(employees.size(), greaterThan(100));

    }
}
