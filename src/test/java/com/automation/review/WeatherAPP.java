package com.automation.review;
import com.automation.utilities.ConfigurationReader;
import io.restassured.response.Response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class WeatherAPP {
    static {
        baseURI = ConfigurationReader.getProperty("METAWEATHER.URI");
    }


    public static void main(String[] args) {
     Scanner scanner = new Scanner(System.in);
        System.out.println("Enter city name: ");
        String city = scanner.nextLine();
        printWeatherInfo(getWOEID(city));
    }



    public static String getWOEID(String city){
        Response response = given().queryParam("query", city).get("/search");
        String woeid = response.jsonPath().getString("woeid");
        //System.out.println("WOEID = " + woeid);
        return woeid;
    }

    public static void printWeatherInfo(String woeid){
        woeid = woeid.replaceAll("\\D", ""); // to delete all non-digits
        Response response = get("{woeid}", woeid);

        List<String> weatherStateName = response.jsonPath().getList("consolidated_weather.weather_state_name");
        System.out.println("weatherStateName = " + weatherStateName);

        List<Double> temp = response.jsonPath().getList("consolidated_weather.the_temp");
        List<String> dates = response.jsonPath().getList("consolidated_weather.applicable_date");
        System.out.println("Here is the weather forecast for this week:");
        for (int i = 0; i < weatherStateName.size() ; i++) {

            String date = dates.get(i);
            date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("MMMM, dd, yyyy"));

            System.out.printf("Date: %s, Weather state: %s, Temperature %s\n", date, weatherStateName.get(i), temp.get(i));
        }

    }

}
