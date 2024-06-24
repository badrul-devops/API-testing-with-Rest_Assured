package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.GlobalVariables;
import utils.Log;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

@Feature("Booking Management")
public class CreateBookingTest {

    @Test
    @Story("Create a new booking")
    @Description("This test creates a new booking and verifies it is created successfully")
    public void createBooking() throws IOException {
        Log.info("Creating Booking");

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        String createBookingJson = getCreateBookingJson();

        Response response = given()
                .header("Content-Type", "application/json")
                .body(createBookingJson)
                .post("/booking");

        response.then().statusCode(200);
        int bookingId = response.jsonPath().getInt("bookingid");
        GlobalVariables.bookingId = bookingId;  // Store the booking ID in global variable
        Log.info("Booking created with ID: " + bookingId);
        Assert.assertTrue(bookingId > 0, "Booking ID should be greater than 0");

    }

    @Step("Prepare create booking JSON")
    private String getCreateBookingJson() throws IOException {
        String filePath = "src/main/resources/CreateBookingTest.json";  // Specify the path to your JSON file
        return new String(Files.readAllBytes(Paths.get(filePath)));
//        return "{ \"firstname\": \"Jim\", \"lastname\": \"Brown\", \"totalprice\": 111, \"depositpaid\": true, \"bookingdates\": { \"checkin\": \"2022-01-01\", \"checkout\": \"2022-01-02\" }, \"additionalneeds\": \"Breakfast\" }";
    }
}
