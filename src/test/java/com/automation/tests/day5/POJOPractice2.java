package com.automation.tests.day5;
import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class POJOPractice2 {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
    }

    @Test
    public void getUser(){
        Response response = given()
                                    .auth().basic("admin", "admin").
                            when()
                                    .get("/spartans/{id}",393).prettyPeek();

        Spartan spartan = response.as(Spartan.class);
        System.out.println(spartan);
    }
}
