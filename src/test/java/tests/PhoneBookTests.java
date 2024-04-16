package tests;

import config.BaseTest;
import helpers.AlertHandler;
import helpers.EmailGenerator;
import helpers.PropertiesReader;
import helpers.TopMenuItem;
import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BasePage;
import pages.ContactsPage;
import pages.LoginPage;
import pages.MainPage;

public class PhoneBookTests extends BaseTest {

    @Test
    public void loginWithoutPasswordPositive() throws InterruptedException {
        MainPage mainPage = new MainPage(getDriver());
        LoginPage loginPage=BasePage.openTopMenuItem(TopMenuItem.LOGIN);
        Alert alert = loginPage.fillEmailField(EmailGenerator.generateEmail(3,3,2)).clickByRegistrationButton();
        String expectedAlertText = "Wrong";
        boolean isAlertHandled= AlertHandler.handleAlert(alert,expectedAlertText);
        Assert.assertTrue(isAlertHandled);


    }
    @Test
    public void registrationOfAnAlreadyRegisteredUser(){
        MainPage mainPage = new MainPage(getDriver());
        LoginPage loginPage = BasePage.openTopMenuItem(TopMenuItem.LOGIN);
        Alert alert = loginPage.fillEmailField("Misha1@test.com").fillPasswordField("MIShaa123!").clickByRegistrationButton();
        String expectedAlertText = "User already exist";
        boolean isAlertHandled= AlertHandler.handleAlert(alert,expectedAlertText);
        Assert.assertTrue(isAlertHandled);
    }





    @Test
    public void successFullLogin() throws InterruptedException {
        MainPage mainPage = new MainPage(getDriver());
        LoginPage loginPage = BasePage.openTopMenuItem(TopMenuItem.LOGIN);
        ContactsPage contactsPage = loginPage.fillEmailField(PropertiesReader.getProperty("myuser")).fillPasswordField(PropertiesReader.getProperty("mypassword")).clickByLoginButton();
        boolean res = contactsPage.containsSignOutButton();
        Assert.assertTrue(res);
    }
}
