package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import requestObject.RequestUser;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "userName")
    private WebElement userName;

    @FindBy(id = "password")
    private WebElement userPassword;

    @FindBy(id = "login")
    private WebElement login;

    @FindBy(id = "name")
    private WebElement errorMessage;

    public void appLogin(RequestUser requestBody) {
        userName.sendKeys(requestBody.getUserName());
        userPassword.sendKeys(requestBody.getPassword());
        login.click();
    }

    public void validateLoginError() {
        String actualError = errorMessage.getText();
        Assert.assertEquals(actualError,"Invalid username or password!");
    }
}
