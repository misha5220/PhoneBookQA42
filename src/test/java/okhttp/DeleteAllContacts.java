package okhttp;

import helpers.PropertiesReaderXML;
import interfaces.TestHelper;
import models.ContactResponseModel;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteAllContacts implements TestHelper  {


    @BeforeTest
    public void loginBeforeDelete() throws IOException {
        LoginTest loginTest = new LoginTest();
        loginTest.loginPositive();
        System.out.println("Your authorization token has been updated");
    }
    @Test
    public void clearContacts() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL+DELETE_ALL_CONTACTS)
                .addHeader(AUTHORIZATION_HEADER, PropertiesReaderXML.getProperty(TOKEN,XML_FILE_PATH))
                .delete()
                .build();
        Response response = CLIENT.newCall(request).execute();
        System.out.println("Code "+response.code());
        ContactResponseModel contactResponseModel = GSON.fromJson(response.body().string(), ContactResponseModel.class);
        System.out.println("Message"+contactResponseModel.getMessage());
        Assert.assertEquals(response.code(),200);

    }
    @Test
    public void clearContactsFromAnEmptyContactList() throws IOException{
        clearContacts();
        clearContacts();
    }
    @Test
    public void clearContactsNegativeToken() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL+DELETE_ALL_CONTACTS)
                .addHeader(AUTHORIZATION_HEADER, "Nalchik")
                .delete()
                .build();
        Response response = CLIENT.newCall(request).execute();
        System.out.println("Code "+response.code());
        ContactResponseModel contactResponseModel = GSON.fromJson(response.body().string(), ContactResponseModel.class);
        System.out.println("Message"+contactResponseModel.getMessage());
        Assert.assertFalse(response.isSuccessful());
    }


}
