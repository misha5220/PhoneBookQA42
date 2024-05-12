package okhttp;

import helpers.*;
import interfaces.TestHelper;
import models.Contact;
import models.ContactResponseModel;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.enterprise.inject.New;
import java.io.IOException;

public class DeleteContact implements TestHelper {

    @Test
    public void createAndDeleteContactPositive() throws IOException {
        Contact contact = new Contact(
                NameAndLastNameGenerator.generateName(),
                NameAndLastNameGenerator.generateLastName(),
                PhoneNumberGenerator.generatePhoneNumber(),
                EmailGenerator.generateEmail(3,3,3),
                AddressGenerator.generateAddress(),"desc");
        RequestBody requestBody = RequestBody.create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+ADD_CONTACT)
                .addHeader(
                        AUTHORIZATION_HEADER, PropertiesReaderXML.getProperty(TOKEN,XML_FILE_PATH))
                .post(requestBody)
                .build();
        Response response = CLIENT.newCall(request).execute();
        ContactResponseModel contactResponseModel =
                GSON.fromJson(response.body().string(), ContactResponseModel.class);
        System.out.println("Message : "+contactResponseModel.getMessage());
        String id = IDExtractor.getID(contactResponseModel.getMessage());
        request = new Request.Builder()
                .url(BASE_URL+DELETE_CONTACTS+id)
                .addHeader(AUTHORIZATION_HEADER, PropertiesReaderXML.getProperty(TOKEN,XML_FILE_PATH))
                        .delete().build();
        response = CLIENT.newCall(request).execute();
        contactResponseModel=GSON.fromJson(response.body().string(), ContactResponseModel.class);
        System.out.println("Message : "+contactResponseModel.getMessage());
        Assert.assertTrue(response.isSuccessful());




    }




}
