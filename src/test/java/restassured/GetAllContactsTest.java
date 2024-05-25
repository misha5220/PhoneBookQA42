package restassured;

import helpers.Logger;
import helpers.PropertiesReaderXML;
import interfaces.TestHelper;
import models.Contact;
import models.ContactListModel;
import org.slf4j.spi.SLF4JServiceProvider;
import org.testng.annotations.Test;

import java.io.Console;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;


import static io.restassured.RestAssured.given;

public class GetAllContactsTest implements TestHelper {


    @Test
    public void getAllContactsPositive() throws IOException, IOException {
        //
        Logger.setupLogger("src/logs/getAllContactsTest.log");

       ContactListModel contactList = given()
                .header(AUTHORIZATION_HEADER,PropertiesReaderXML.getProperty("token",XML_FILE_PATH))
                .get(BASE_URL+GET_ALL_CONTACTS)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .as(ContactListModel.class);
        for (Contact contact:contactList.getContacts()){
            System.out.println(contact.getName());
            System.out.println(contact.getEmail());
        }

        Logger.closeLogger();
    }

}
