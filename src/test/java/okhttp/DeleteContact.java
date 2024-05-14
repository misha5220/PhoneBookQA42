package okhttp;

import helpers.*;
import interfaces.TestHelper;
import models.Contact;
import models.ContactResponseModel;
import models.ErrorModel;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteContact implements TestHelper {
    String id;
    @BeforeMethod
    public void createContactPrecondition() throws IOException {
        Contact contact = new Contact(
                NameAndLastNameGenerator.generateName(),
                NameAndLastNameGenerator.generateLastName(),
                PhoneNumberGenerator.generatePhoneNumber(),
                EmailGenerator.generateEmail(5,5,3),
                AddressGenerator.generateAddress(),"desc");
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+ADD_CONTACT)
                .addHeader(
                        AUTHORIZATION_HEADER,
                        PropertiesReaderXML.getProperty("token",TestHelper.XML_FILE_PATH))
                .post(requestBody)
                .build();
        Response response = CLIENT.newCall(request).execute();
        ContactResponseModel contactResponseModel =
                GSON.fromJson(response.body().string(), ContactResponseModel.class);
        System.out.println("Message : "+contactResponseModel.getMessage());
        id = IDExtractor.getID(contactResponseModel.getMessage());
    }
    @Test
    public void createAndDeleteContactPositive() throws IOException {

       Request request = new Request.Builder()
                .url(BASE_URL + DELETE_CONTACTS + id)
                .addHeader(AUTHORIZATION_HEADER,
                        PropertiesReaderXML.getProperty("token", TestHelper.XML_FILE_PATH))
                .delete()
                .build();
        Response response = CLIENT.newCall(request).execute();
        ContactResponseModel contactResponseModel =
                GSON.fromJson(response.body().string(), ContactResponseModel.class);
        System.out.println(contactResponseModel.getMessage());
        Assert.assertTrue(response.isSuccessful());
    }

    @Test
    public void deleteContactByID() throws IOException {
        Request request = new Request.Builder().url(BASE_URL+DELETE_CONTACTS+id)
                .addHeader(AUTHORIZATION_HEADER, PropertiesReaderXML.getProperty("token", TestHelper.XML_FILE_PATH))
                .delete().build();
        Response response = CLIENT.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
    }
    @Test
    public void createAndDeleteContactByIdNegative() throws IOException {

        Request request = new Request.Builder().url(BASE_URL+DELETE_CONTACTS+id)
                .addHeader(AUTHORIZATION_HEADER,  "df")
                .delete().build();
        Response response = CLIENT.newCall(request).execute();
        ErrorModel errorModel = GSON.fromJson(response.body().string(), ErrorModel.class);
        System.out.println(errorModel.getStatus());
        Assert.assertEquals(errorModel.getStatus(), 401);
    }

}