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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static io.restassured.RestAssured.given;

@Feature("Booking Management")
public class UpdateBookingTest {

    @Test
    @Story("Update an existing booking")
    @Description("This test updates an existing booking and verifies it is updated successfully")
    public void updateBooking() throws FileNotFoundException {
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
        Assert.assertEquals(response.jsonPath().getString("lastname"), "Doe", "Lastname name should be updated to Doe");
    }

    @Step("Prepare update booking JSON")
    private String getUpdateBookingJson() throws FileNotFoundException {
        String filePath = "src/main/resources/CreateBookingTest.json";
        JsonObject jsonObject = JsonParser.parseReader(new FileReader(filePath)).getAsJsonObject();
        jsonObject.addProperty("lastname", "Doe");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }
}
