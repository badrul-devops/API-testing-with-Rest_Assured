package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.GlobalVariables;
import utils.Log;

import static io.restassured.RestAssured.given;

@Feature("Booking Management")
public class GetBookingTest {

    @Test
    @Story("Get a booking")
    @Description("This test retrieves a booking by its ID")
    public void getBooking() {
        Log.info("Getting Booking");

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        int bookingId = GlobalVariables.bookingId; // Use global booking ID

        Response response = given()
                .header("Content-Type", "application/json")
                .get("/booking/" + bookingId);
        response.then().statusCode(200);
        Log.info("Retrieved Booking: " + response.asString());
    }
}
