package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.GlobalVariables;
import utils.Log;

import static io.restassured.RestAssured.given;

@Feature("Booking Management")
public class DeleteBookingTest {

    @Test
    @Story("Delete an existing booking")
    @Description("This test deletes an existing booking and verifies that it is deleted successfully")
    public void deleteBooking() {
        Log.info("Deleting Booking");

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        int bookingId = GlobalVariables.bookingId;  // Use global booking ID
        String token = GlobalVariables.token;  // Use global token

        Response response = given()
                .header("Content-Type", "application/json")
                .header("Cookie", "token=" + token)
                .delete("/booking/" + bookingId);

        response.then().statusCode(201);
        Log.info("Booking deleted: " + bookingId);
        Assert.assertEquals(response.getStatusCode(), 201, "Status code should be 201");

    }
}
