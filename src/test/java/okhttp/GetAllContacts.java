package okhttp;

import helpers.PropertiesReaderXML;
import interfaces.TestHelper;
import models.Contact;
import models.ContactListModel;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetAllContacts implements TestHelper  {

    @Test
    public void getAllContacts() throws IOException {
        Request request = new Request.Builder().url(BASE_URL+GET_ALL_CONTACTS).addHeader(AUTHORIZATION_HEADER, PropertiesReaderXML.getProperty(TOKEN,XML_FILE_PATH)).build();
        Response response = CLIENT.newCall(request).execute();
        String res=response.body().string();

        ContactListModel contacts = GSON.fromJson(res, ContactListModel.class);

        for (Contact contact : contacts.getContacts()){
            System.out.println(contact);
            System.out.println("============================================");
        }
        Assert.assertTrue(response.isSuccessful());
    }
}
