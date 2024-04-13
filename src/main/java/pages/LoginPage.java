package pages;

import com.beust.ah.A;
import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//input[@name='email']")
    WebElement emailField;
    @FindBy(xpath = "//input[@name='password']")
    WebElement passwordField;
    @FindBy(xpath = "//button[@name='login']")
    WebElement loginButton;

    @FindBy(xpath = "//button[@name='registration']")
    WebElement registrationButton;

    public LoginPage(WebDriver driver) {
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
    }

    public LoginPage fillEmailField(String email) {
        emailField.sendKeys(email);
        return this;
    }

    public LoginPage fillPasswordField(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    public Alert clickByRegistrationButton(){
        registrationButton.click();
        return getAlertIfPresent();
    }

    private Alert getAlertIfPresent(){
        short time =5;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(time));
            return wait.until(ExpectedConditions.alertIsPresent());
        }catch (TimeoutException e){
            System.out.println("There is no alert");
            return null;
        }
    }
    public <T extends BasePage> T clickByLoginButton(){
        loginButton.click();
        Alert alert = getAlertIfPresent();
        if(alert != null){
            alert.accept();
            return (T) new LoginPage(driver);
        }else {return (T) new ContactsPage(driver);}
    }
}
