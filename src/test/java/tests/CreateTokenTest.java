package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import static io.restassured.RestAssured.given;
import utils.GlobalVariables;
import utils.Log;

@Feature("Authentication")
public class CreateTokenTest {

    @Test
    @Story("Create an API token")
    @Description("This test generates an API token for authentication")
    public void createToken() {
        Log.info("Creating API Token");

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        String createTokenJson = getCreateTokenJson();

        Response response = given()
                .header("Content-Type", "application/json")
                .body(createTokenJson)
                .post("/auth");

        response.then().statusCode(200);
        String token = response.jsonPath().getString("token");
        GlobalVariables.token = token;  // Store the token in global variable
        Log.info("Token generated: " + token);
    }

    @Step("Prepare create token JSON")
    private String getCreateTokenJson() {
        return "{ \"username\": \"admin\", \"password\": \"password123\" }";
    }
}
