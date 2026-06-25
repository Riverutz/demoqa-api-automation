package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProfilePage;
import requestObject.RequestUser;
import responseObject.ResponseToken;
import responseObject.ResponseUser;
import services.AccountService;
import java.time.Duration;

public class CreateUserFETest {

    public AccountService accountService;
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
        System.out.println("===== STEP 4: LOGIN ACCOUNT =====");
        applicationLogin();

        System.out.println("");
        System.out.println("===== STEP 5: DELETE ACCOUNT =====");
        deleteAccountFE();

        System.out.println("");
        System.out.println("===== STEP 6: GET USER DETAILS =====");
        validateAccountBE();
    }

    public void createAccount() {
        requestBody = new RequestUser("src/test/resources/createUser.json");
        accountService = new AccountService();

        ResponseUser responseBody = accountService.createAccount(requestBody);
        userId = responseBody.getUserId();
    }

    public void generateToken() {
        ResponseToken responseBody = accountService.generateToken(requestBody);
        token = responseBody.getToken();
    }

    public void validateAccountBE() {
        accountService.validateAccountBE(token, userId);
    }

    public void applicationLogin() {
        driver = new ChromeDriver();
        driver.get("https://demoqa.com/login");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        LoginPage loginPage = new LoginPage(driver);
        loginPage.appLogin(requestBody);

        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.validateLoginProcess(requestBody);
    }

    public void deleteAccountFE() {
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.deleteAccount();
    }
}