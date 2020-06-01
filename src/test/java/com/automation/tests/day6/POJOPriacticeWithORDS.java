package com.automation.tests.day6;
import com.automation.pojos.Link;
import io.restassured.http.ContentType;
import com.automation.pojos.Employee;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.automation.utilities.ConfigurationReader;


import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;

public class POJOPriacticeWithORDS {
    @BeforeAll
    public static void setup() {
        baseURI = ConfigurationReader.getProperty("ORDS.URI");
    }

    @Test
    public void getEmployeeTest(){
        Response response = get("/employees/{id}", 100).prettyPeek();
        Employee employee = response.as(Employee.class);
        System.out.println(employee);

        List<Link> link = employee.getLinks();
        System.out.println(link);
    }
}


