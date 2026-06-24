package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import requestObject.RequestUser;

public class ProfilePage extends BasePage {
    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "userName-value")
    private WebElement userNameValue;

    @FindBy(xpath = "//button[text()='Delete Account']")
    private WebElement deleteButton;

    @FindBy(id = "closeSmallModal-ok")
    private WebElement clickOkButton;


    public void validateLoginProcess(RequestUser requestBody) {
        String actualUserName = userNameValue.getText();
        Assert.assertEquals(actualUserName, requestBody.getUserName());
    }

    public void deleteAccount(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.confirm = function(){return true;}; window.alert = function(){return true;};");

        deleteButton.click();
        clickOkButton.click();
    }
}

