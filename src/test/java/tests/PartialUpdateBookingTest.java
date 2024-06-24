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

import static io.restassured.RestAssured.given;

@Feature("Booking Management")
public class PartialUpdateBookingTest {

    @Test
    @Story("Partially update an existing booking")
    @Description("This test partially updates an existing booking and verifies that it is updated successfully")
    public void partialUpdateBooking() {
        Log.info("Partially Updating Booking");

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        int bookingId = GlobalVariables.bookingId;  // Use global booking ID
        String token = GlobalVariables.token;  // Use global token
        String partialUpdateJson = getPartialUpdateJson();

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .body(partialUpdateJson)
                .patch("/booking/" + bookingId);

        response.then().statusCode(200);
        Log.info("Booking partially updated: " + response.asString());
        Assert.assertEquals(response.jsonPath().getString("firstname"), "John", "First name should be updated to John");
        Assert.assertEquals(response.jsonPath().getString("lastname"), "Smith", "First name should be updated to Smith");
    }

    @Step("Prepare partial update JSON")
    private String getPartialUpdateJson() {

        return "{ \"firstname\": \"John\", \"lastname\": \"Smith\" }";
    }
}
