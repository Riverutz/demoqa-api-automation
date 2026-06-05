package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import requestObject.RequestUser;
import responseObject.ResponseToken;
import responseObject.ResponseUser;

public class CreateUserTest {

    public String baseUri = "https://demoqa.com";
    public RequestUser requestBody;

    @Test
    public void testMethod() {
        System.out.println("===== STEP 1: CREATE ACCOUNT =====");
        createAccount();
        System.out.println("");
        System.out.println("===== STEP 2: GENERATE TOKEN =====");
        generateToken();

    }

    public void createAccount() {
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.baseUri(baseUri);

        requestBody = new RequestUser("src/test/resources/createUser.json");

        System.out.println(requestBody);
        request.body(requestBody);

        Response response = request.post("/Account/v1/User");

        System.out.println(response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(response.getStatusLine().contains("Created"));

        ResponseUser responseBody = response.getBody().as(ResponseUser.class);
        Assert.assertEquals(requestBody.getUserName(), responseBody.getUsername());
        System.out.println(responseBody);
    }

    public void generateToken() {
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.baseUri(baseUri);

        request.body(requestBody);

        Response response = request.post("/Account/v1/GenerateToken");

        System.out.println(response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getStatusLine().contains("OK"));

        ResponseToken responseBody = response.getBody().as(ResponseToken.class);

        System.out.println(responseBody.getToken());

        Assert.assertNotNull(responseBody.getToken(), "Token should not be null");

        System.out.println(responseBody);
    }
}
