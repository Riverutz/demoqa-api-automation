import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateUserTest {

    public String baseUri = "https://demoqa.com";
    public JSONObject requestBody;

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

        String userName = "danielTesting" + System.currentTimeMillis();

        requestBody = new JSONObject();
        requestBody.put("userName", userName);
        requestBody.put("password", "Automation@223@!");
        request.body(requestBody.toString());

        Response response = request.post("/Account/v1/User");

        System.out.println(response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(response.getStatusLine().contains("Created"));

        ResponseBody responseBody = response.getBody();
        Assert.assertTrue(responseBody.asPrettyString().contains(userName));
    }

    public void generateToken() {
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.baseUri(baseUri);

        request.body(requestBody.toString());

        Response response = request.post("/Account/v1/GenerateToken");

        System.out.println(response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getStatusLine().contains("OK"));

        ResponseBody responseBody = response.getBody();
        Assert.assertTrue(responseBody.asPrettyString().contains("token"));
    }
}
