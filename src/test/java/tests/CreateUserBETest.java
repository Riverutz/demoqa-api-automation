package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import requestObject.RequestUser;
import responseObject.ResponseToken;
import responseObject.ResponseUser;

import java.time.Duration;

public class CreateUserBETest {

    public String baseUri = "https://demoqa.com";
    public RequestUser requestBody;
    public WebDriver driver;
    public String userId;
    public String token;

    @Test
    public void testMethod() {
        System.out.println("===== STEP 1: CREATE ACCOUNT =====");
        createAccount();

        System.out.println("");
        System.out.println("===== STEP 2: GENERATE TOKEN =====");
        generateToken();

        System.out.println("");
        System.out.println("===== STEP 3: GET USER DETAILS =====");
        validateAccountBE();

        System.out.println("");
        System.out.println("===== STEP 4: DELETE ACCOUNT =====");
        deleteAccountBE();

        System.out.println("");
        System.out.println("===== STEP 5: LOGIN ACCOUNT =====");
        applicationLogin();

        System.out.println("");
        System.out.println("===== STEP 6: GET USER DETAILS =====");
        validateAccountBE();
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

        userId = responseBody.getUserId();
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

        token = responseBody.getToken();
    }

    public void validateAccountBE() {
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.baseUri(baseUri);

        request.header("Authorization", "Bearer " + token);

        Response response = request.get("/Account/v1/User/" + userId);
        response.body().prettyPrint();
    }

    public void applicationLogin() {
        driver = new ChromeDriver();
        driver.get("https://demoqa.com/login");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.appLogin(requestBody);
        loginPage.validateLoginError();
    }

    public void deleteAccountBE() {
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.baseUri(baseUri);

        request.header("Authorization", "Bearer " + token);

        Response response = request.delete("/Account/v1/User/" + userId);
        response.body().prettyPrint();
    }
}
