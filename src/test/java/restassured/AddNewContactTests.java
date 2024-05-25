package restassured;

import helpers.*;
import interfaces.TestHelper;
import io.restassured.http.ContentType;
import models.Contact;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AddNewContactTests implements TestHelper {
    @Test
    public void addNewContactPositive(){
        Contact contact = new Contact(
                NameAndLastNameGenerator.generateName(),
                NameAndLastNameGenerator.generateLastName(),
                PhoneNumberGenerator.generatePhoneNumber(),
                EmailGenerator.generateEmail(3,3,3),
                AddressGenerator.generateAddress(),"desc");

        given().header(AUTHORIZATION_HEADER, PropertiesReaderXML.getProperty("token",XML_FILE_PATH)).body(contact).contentType(ContentType.JSON)
                .post(BASE_URL+ADD_CONTACT).then().log().all().statusCode(200);
    }

}
