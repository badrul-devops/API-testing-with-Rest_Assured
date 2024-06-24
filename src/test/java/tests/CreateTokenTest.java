package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import static io.restassured.RestAssured.given;
import utils.GlobalVariables;
import utils.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Feature("Authentication")
public class CreateTokenTest {

    @Test
    @Story("Create an API token")
    @Description("This test generates an API token for authentication")
    public void createToken() throws IOException {
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
        Assert.assertNotNull(token, "Token should not be null");
    }

    @Step("Prepare create token JSON")
    private String getCreateTokenJson() throws IOException {
        String filePath = "src/main/resources/CreateToken.json";  // Specify the path to your JSON file
        return new String(Files.readAllBytes(Paths.get(filePath)));

    }
}
