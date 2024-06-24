package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Log;

import static io.restassured.RestAssured.given;

@Feature("Booking Management")
public class GetBookingIdsTest {

    @Test
    @Story("Get booking IDs")
    @Description("This test retrieves all booking IDs")
    public void getBookingIds() {
        Log.info("Getting Booking IDs");

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        Response response = given()
                .header("Content-Type", "application/json")
                .get("/booking");

        response.then().statusCode(200);
        Log.info("Retrieved Booking IDs: " + response.asString());
        Assert.assertFalse(response.jsonPath().getList("bookingid").isEmpty(), "Booking IDs list should not be empty");
    }
}
