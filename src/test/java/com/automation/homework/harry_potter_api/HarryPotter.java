package com.automation.homework.harry_potter_api;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class HarryPotter {

    @BeforeAll
    public static void setup() {
        baseURI = "https://www.potterapi.com/v1/";
    }

/*
Automate the given test cases.
You can use any existing project.
You can automate all test cases in same class or different classes.
For verifying all of the use pojos.
Create pojo classes for Character and House in
pojos package based on the provided json files.

Verify sorting hat
1.Send a get request to /sortingHat.
Request includes :2.Verify status code 200,
content type application/json; charset=utf-8
3.Verify that response body contains one of the
following houses: "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
 */

    @Test
    public void verifyResponseBodyContainsOneOfHouses() {
        Response response =
                given().
                        queryParam("apiKey", "$2a$10$8U30d60AJUAvvY407fdewOndCLvGySAgG8GU2UXfzSqkX4tRzPswS").
                        when().
                        get("/sortingHat");
        response.then().
                statusCode(200).
                contentType(ContentType.JSON);
        String school = response.body().asString().replace("\"", "");
        System.out.println(school);

        List<String> list = new ArrayList<>(Arrays.asList("Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"));
        assertTrue(list.contains(school));
    }

    /*
    Verify bad key
    1.Send a get request to /characters.
    Request includes :
    •Header Accept with value application/json
    •Query param key with value invalid
    2.Verify status code 401, content type application/json; charset=utf-8
    3.Verify response status line include message Unauthorized
    4.Verify that response body says"error":"APIKeyNotFound"
     */
    @Test
    @DisplayName("Verify that response body says error:APIKeyNotFound")
    public void verifyResponseBodyError() {
        Response response =
                given().
                        queryParam("apiKey", "$2a$10$8U30d60AJUAvvY407fdewOndCLvGySAgG8GU2UXfzSqkX4tRzPswS").
                        queryParam("key", "invalid").
                        header("Accept", "application/json").
                        when().
                        get("/characters").prettyPeek();
        response.then().
                statusCode(401).
                contentType(ContentType.JSON);

        assertTrue(response.getStatusLine().contains("Unauthorized"));
        assertTrue(response.body().asString().contains("\"error\":\"API Key Not Found\""));
    }

    /*
    Verify no key
    1.Send a get request to /characters.
    Request includes :
    •Header Accept with value application/json
    2.Verify status code 409, content type application/json; charset=utf-8
    3.Verify response status line include message Conflict
    4.Verify that response body says"error":"Must pass API key for request"
     */
    @Test
    @DisplayName("Verify that response body says\"error\":\"Must pass API key for request\"")
    public void verifyErrorMustPassKey() {
        Response response =
                given().
                        queryParam("apiKey", "$2a$10$8U30d60AJUAvvY407fdewOndCLvGySAgG8GU2UXfzSqkX4tRzPswS").
                        header("Accept", "application/json").
                        when().
                        get("/characters").prettyPeek();
        response.then().
                statusCode(409).
                contentType(ContentType.JSON);

        assertTrue(response.getStatusLine().contains("Conflict"));
        assertTrue(response.body().asString().contains("\"error\":\"Must pass API key for request\""));
    }

    /*
    Verify number of characters
    1.Send a get request to /characters.
    Request includes :
    •Header Accept with value application/json
    •Query param key with value {{apiKey}}
    2.Verify status code 200, content type application/json; charset=utf-83.
    Verify response contains 194 characters
     */
    @Test
    @DisplayName("Verify response contains 194 characters")
    public void verifyNumberOfCharacters() throws IOException {
        Response response =
                given().
                        queryParam("key", "$2a$10$8U30d60AJUAvvY407fdewOndCLvGySAgG8GU2UXfzSqkX4tRzPswS").
                        // queryParam("key","{{apiKey}}").
                                header("Accept", "application/json").
                        when().
                        get("/characters").prettyPeek();
        response.then().
                statusCode(200).
                contentType(ContentType.JSON);

        List<Character> characters = response.jsonPath().getList("", Character.class);
        assertThat(characters.size(), is(194));  // Test Failed, expected 194, actual is 195
    }

    /*
    Verify number of character id and house
    1.Send a get request to /characters.
    Request includes :
    •Header Accept with value application/json
    •Query param key with value {{apiKey}}

    2.Verify status code 200, content type application/json; charset=utf-83.
    Verify all characters in the response have id field which is not empty
    4.Verify that value type of the field dumbledoresArmy is a boolean
     in all characters in the response
     5.Verify value of the house in all characters in the response is one
     of the following: "Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"
     */
    @Test
    @DisplayName("Verify number of character id and house")
    public void verifyCharacterIdAndHouse() throws IOException {
        Response response =
                given().
                        queryParam("key", "$2a$10$8U30d60AJUAvvY407fdewOndCLvGySAgG8GU2UXfzSqkX4tRzPswS").
                        header("Accept", "application/json").
                        when().
                        get("/characters");
        response.then().
                statusCode(200).
                contentType(ContentType.JSON);

        List<Character> characterList = response.jsonPath().getList("", Character.class);

        assertFalse(characterList.contains(null));
        boolean fieldType = true;
        for (Character each : characterList) {
            if (!each.isDumbledoresArmy() == true && !each.isDumbledoresArmy() == false) {
                fieldType = false;
                //System.out.println(each.isDumbledoresArmy());
            }
        }
        assertThat(fieldType, is(true));

        boolean isCharacterInHouse = true;
        List<String> houses = new ArrayList<>(Arrays.asList("Gryffindor", "Ravenclaw", "Slytherin", "Hufflepuff"));
        for (Character each : characterList) {
            System.out.println(each.getHouse());
            if (!houses.contains(each.getHouse())) {
                isCharacterInHouse = false;
                System.out.println(each.getHouse());
            }
        }
        assertThat(isCharacterInHouse, is(true));
        //Test fails as some of values are null and don't belong to any House
    }

    /*
    Verify all character information
    1.Send a get request to /characters.
    Request includes :
    •Header Accept with value application/json•Query param key with value {{apiKey}}
    2.Verify status code 200, content type application/json; charset=utf-8
    3.Select name of any random character
    4.Send a get request to /characters.

    Request includes :
    •Header Accept with value application/json
    •Query param key with value {{apiKey}}
    •Query param name with value from step 3
    5.Verify that response contains the same character information from step 3.
    Compare all fields.
     */
    @Test
    @DisplayName("Verify all character information")
    public void verifyCharacterInformation() {
        Response response =
                given().
                        queryParam("key", "$2a$10$8U30d60AJUAvvY407fdewOndCLvGySAgG8GU2UXfzSqkX4tRzPswS").
                        // queryParam("key","{{apiKey}}").
                                header("Accept", "application/json").
                        when().
                        get("/characters");
        response.then().
                statusCode(200).
                contentType(ContentType.JSON);

        List<Character> allCharacters = response.jsonPath().getList("", Character.class);

        Random random = new Random();
        int randomNumber = random.nextInt(allCharacters.size());
        String randomName = allCharacters.get(randomNumber).getName();
        System.out.println("randomName = " + randomName);

        Response response1 =
                given().
                        queryParam("key", "$2a$10$8U30d60AJUAvvY407fdewOndCLvGySAgG8GU2UXfzSqkX4tRzPswS").
                        queryParam("name", randomName).
                        // queryParam("key","{{apiKey}}").
                                header("Accept", "application/json").
                        when().
                        get("/characters");
        response1.then().
                statusCode(200).
                contentType(ContentType.JSON);
        String actualName = response1.body().path("name").toString().replace("[", "").replace("]", "");
        System.out.println("actualName = " + actualName);
        assertThat(randomName, is(actualName));
    }

    /*Verify name search
    1.Send a get request to /characters.
    Request includes :
    •Header Accept with value application/json
    •Query param key with value {{apiKey}}
    •Query param name with value Harry Potter

    2.Verify status code 200, content type application/json; charset=utf-83.
    Verify name Harry Potter

    4.Send a get request to /characters.
    Request includes :
    •Header Accept with value application/json
    •Query param key with value {{apiKey}}
    •Query param name with value Marry Potter
    5.Verify status code 200, content type application/json; charset=utf-8
    6.Verify response body is empty

     */
    @Test
    @DisplayName("Verify name search")
    public void verifyNameSearch() {
        Response response =
                given().
                        queryParam("key", "$2a$10$8U30d60AJUAvvY407fdewOndCLvGySAgG8GU2UXfzSqkX4tRzPswS").
                        queryParam("name", "Harry Potter").
                        // queryParam("key","{{apiKey}}").
                        header("Accept", "application/json").
                        when().
                        get("/characters").prettyPeek();
        response.then().
                statusCode(200).
                contentType(ContentType.JSON);
        String name = response.jsonPath().getString("name").replace("[", "").replace("]", "");

        assertThat(name, is("Harry Potter"));

        Response response1 =
                given().
                        queryParam("key", "$2a$10$8U30d60AJUAvvY407fdewOndCLvGySAgG8GU2UXfzSqkX4tRzPswS").
                        queryParam("name", "Marry Potter").
                        // queryParam("key","{{apiKey}}").
                                header("Accept", "application/json").
                        when().
                        get("/characters").prettyPeek();
        response1.then().
                statusCode(200).
                contentType(ContentType.JSON);

        assertThat(response1.body().asString(), is("[]"));
    }

    /*
    Verify house members
    1.Send a get request to /houses.
    Request includes :
    •Header Accept with value application/json
    •Query param key with value {{apiKey}}

    2.Verify status code 200, content type application/json; charset=utf-8
    3.Capture the id of the Gryffindor house
    4.Capture the ids of the all members of the Gryffindor house

    5.Send a get request to /houses/:id.
    Request includes :
    •Header Accept with value application/json
    •Query param key with value {{apiKey}}
    •Path param id with value from step 3
    6.Verify that response contains the  same member ids as the step 4
     */
    @Test
    @DisplayName("Verify house members Gryffindor")
    public void verifyHouseMembers() throws IOException {
        Response response =
                given().
                        queryParam("key", "$2a$10$8U30d60AJUAvvY407fdewOndCLvGySAgG8GU2UXfzSqkX4tRzPswS").
                        header("Accept", "application/json").
                when().
                        get("/houses").prettyPeek();
     
    }
}