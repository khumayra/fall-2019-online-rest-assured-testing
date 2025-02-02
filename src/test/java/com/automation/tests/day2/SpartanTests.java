package com.automation.tests.day2;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.Connection;

import static io.restassured.RestAssured.given;

public class SpartanTests {


    String BASE_URL = "http://3.90.112.152:8000";
    //URI (Uniform Resource Identifier) = URL+URN = http://www.google.com/index.html
    //URL (Uniform Resource Locator) = http://www.google.com
    //URN (Uniform Resource Name) = /index.html

    @Test
    @DisplayName("Get list of all spartans")
    public void getAllSpartans() {
        //481 - unauthorized
        //there are different type of authentication: basic, oath, oath 2.0, api key, bearer, taken, etc...
        //spartan requires
        given().
                auth().basic("admin", "admin").
                baseUri(BASE_URL).
                when().
                get("/api/spartans").prettyPeek().
                then().statusCode(200);
    }

    @Test
    @DisplayName("Add new spartan")
    public void addSpartan() {
        //JSON supports different data types: string, integer, boolean
        String body = "{\"gender\": \"Male\", \"name\": \"Random User\", \"phone\": 9999999999}";
        //instead of string variable, we can use external JSON file
        //use File class to read JSON and pass it into body
        //provide path to the JSON as a parameter
        File jsonFile = new File(System.getProperty("user.dir") + "/spartan.json");

        //to create new item, we perform POST request
        //contentType(ContentType.JSON) - to tell web service what kind of media type we send

        given().
                contentType(ContentType.JSON).
                auth().basic("admin", "admin").
                body(jsonFile).
                baseUri(BASE_URL).
        when().
                post("/api/spartans").prettyPeek().
        then().
                statusCode(201);

    }

    @Test
    @DisplayName("Delete some spartan")
    public void deleteSpartanTest(){
        given()
                .auth().basic("admin", "admin").
                baseUri(BASE_URL).
        when()
                .delete("/api/spartans/{id}", 892).prettyPeek().
        then()
                .statusCode(204);
        //204 - means not content, most common  status code for successful delete action
    }
   }






