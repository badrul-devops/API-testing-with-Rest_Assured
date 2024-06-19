package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.GlobalVariables;
import utils.Log;

import static io.restassured.RestAssured.given;

@Feature("Booking Management")
public class UpdateBookingTest {

    @Test
    @Story("Update an existing booking")
    @Description("This test updates an existing booking and verifies it is updated successfully")
    public void updateBooking() {
        Log.info("Updating Booking");

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        int bookingId = GlobalVariables.bookingId;  // Use global booking ID
        String token = GlobalVariables.token;  // Use global token
        String updateBookingJson = getUpdateBookingJson();

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .body(updateBookingJson)
                .put("/booking/" + bookingId);

        response.then().statusCode(200);
        Log.info("Booking updated: " + response.asString());
    }

    @Step("Prepare update booking JSON")
    private String getUpdateBookingJson() {
        return "{ \"firstname\": \"John\", \"lastname\": \"Doe\", \"totalprice\": 150, \"depositpaid\": true, \"bookingdates\": { \"checkin\": \"2022-01-01\", \"checkout\": \"2022-01-02\" }, \"additionalneeds\": \"Dinner\" }";
    }
}
