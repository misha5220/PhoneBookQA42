package tests;

import config.BaseTest;
import helpers.*;
import io.qameta.allure.Allure;
import modals.Contact;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

import java.io.IOException;

public class PhoneBookTests extends BaseTest {

    @Test
    public void loginWithoutPasswordPositive() throws InterruptedException {
        MainPage mainPage = new MainPage(getDriver());
        LoginPage loginPage=BasePage.openTopMenuItem(TopMenuItem.LOGIN);
        Alert alert = loginPage.fillEmailField(EmailGenerator.generateEmail(3,3,2)).clickByRegistrationButton();
        String expectedAlertText = "Wrong";
        boolean isAlertHandled= AlertHandler.handleAlert(alert,expectedAlertText);
        TakeScreen.takeScreenShot(getDriver(), "LoginWithoutPassword");
        Assert.assertTrue(isAlertHandled);


    }
    @Test
    public void loginOfAnExistingUserAddContact(){
        MainPage mainPage = new MainPage(getDriver());
        LoginPage loginPage = BasePage.openTopMenuItem(TopMenuItem.LOGIN);
        loginPage
                .fillEmailField(PropertiesReader.getProperty("myuser"))
                .fillPasswordField(PropertiesReader.getProperty("mypassword"))
                .clickByLoginButton();
        AddPage addPage = BasePage.openTopMenuItem(TopMenuItem.ADD);
        Contact contact = new Contact(NameAndLastNameGenerator.generateName(),
                NameAndLastNameGenerator.generateLastName(),
                PhoneNumberGenerator.generatePhoneNumber(),
                EmailGenerator.generateEmail(5,5,3),
                AddressGenerator.generateAddress(),"desc");
        addPage.fillContactFormAndSave(contact);
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Assert.assertTrue(contactsPage.getDataFromContactList(contact));

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

    @Test
    public void deleteContactWithSerialization() throws IOException {
        MainPage mainPage = new MainPage(getDriver());
        LoginPage loginPage = BasePage.openTopMenuItem(TopMenuItem.LOGIN);
        loginPage
                .fillEmailField(PropertiesReader.getProperty("myuser"))
                .fillPasswordField(PropertiesReader.getProperty("mypassword"))
                .clickByLoginButton();
        AddPage addPage = BasePage.openTopMenuItem(TopMenuItem.ADD);
        Contact contact = new Contact(NameAndLastNameGenerator.generateName(),
                NameAndLastNameGenerator.generateLastName(),
                PhoneNumberGenerator.generatePhoneNumber(),
                EmailGenerator.generateEmail(5,5,3),
                AddressGenerator.generateAddress(),"desc");
        addPage.fillContactFormAndSave(contact);
        String fileName = "contactDataFile.ser";
        Contact.serializeContact(contact,fileName);
        ContactsPage contactsPage = new ContactsPage(getDriver());
        Contact deserializedContact = Contact.deserializationContact(fileName);
        Assert.assertNotEquals(contactsPage.deleteContactByPhoneNumber(deserializedContact.getPhone()),
                contactsPage.getContactListSize());
    }

    @Test
    public void successfulRegistration() throws InterruptedException {
        Allure.description("Successful registration test.");
        MainPage mainPage = new MainPage(getDriver());
        LoginPage loginPage = BasePage.openTopMenuItem(TopMenuItem.LOGIN);
        Alert alert = loginPage.fillEmailField(EmailGenerator.generateEmail(31, 3, 2))
                .fillPasswordField(PasswordStringGenerator.generatePassword())
                .clickByRegistrationButton();
        if (alert == null) {
            ContactsPage contactsPage = new ContactsPage(getDriver());
            Assert.assertTrue(contactsPage.isElementPersist(getDriver()
                    .findElement(By.xpath("//button[contains(text(),'Sign Out')]"))));

        } else {
            Thread.sleep(3000);
            TakeScreen.takeScreenShot(getDriver(), "Successful Registration");
            Thread.sleep(3000);
        }

    }
}
