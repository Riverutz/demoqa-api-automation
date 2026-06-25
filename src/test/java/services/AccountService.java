package services;

import client.ApiClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import requestObject.RequestUser;
import responseObject.ResponseGetUser;
import responseObject.ResponseGetUserFailed;
import responseObject.ResponseToken;
import responseObject.ResponseUser;

public class AccountService {

    public ResponseUser createAccount(RequestUser requestBody) {
        RequestSpecification request = RestAssured.given();
        request.body(requestBody);

        Response response = performRequest("POST", request, "/Account/v1/User");

        System.out.println(response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertTrue(response.getStatusLine().contains("Created"));

        ResponseUser responseBody = response.getBody().as(ResponseUser.class);
        Assert.assertEquals(requestBody.getUserName(), responseBody.getUsername());
        System.out.println(responseBody);

        return responseBody;
    }

    public ResponseToken generateToken(RequestUser requestBody) {
        RequestSpecification request = RestAssured.given();
        request.body(requestBody);

        Response response = performRequest("POST", request, "/Account/v1/GenerateToken");

        System.out.println(response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.getStatusLine().contains("OK"));

        ResponseToken responseBody = response.getBody().as(ResponseToken.class);

        System.out.println(responseBody.getToken());

        Assert.assertNotNull(responseBody.getToken(), "Token should not be null");

        System.out.println(responseBody);

        return responseBody;
    }

    public void validateAccountBE(String token, String userId) {
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token);

        Response response = performRequest("GET", request, "/Account/v1/User/" + userId);

        if (response.getStatusCode() == 200) {
            ResponseGetUser responseBody = response.getBody().as(ResponseGetUser.class);
            System.out.println(responseBody.getUserId());
            System.out.println(responseBody);
        } else {
            ResponseGetUserFailed responseBody = response.getBody().as(ResponseGetUserFailed.class);
            System.out.println(responseBody.getMessage());
        }
    }

    public void deleteAccountBE(String token, String userId) {
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token);

        Response response = performRequest("DELETE", request, "/Account/v1/User/" + userId);
        response.body().prettyPrint();
    }

    private Response performRequest(String requestType, RequestSpecification requestSpecification, String endpoint) {
        return new ApiClient().performRequest(requestType, requestSpecification, endpoint);
    }
}
