package restassured;

import helpers.*;
import interfaces.TestHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.Contact;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class UpdateContactTests implements TestHelper {
    String id;
    Contact contact;

    @BeforeTest
    public void ContactGenerator (){
        RestAssured.baseURI= BASE_URL+UPDATE_CONTACTS;
        contact = RandomContactGenerator.contactGenerator();
        String message = given().header(AUTHORIZATION_HEADER, PropertiesReaderXML.getProperty("token",XML_FILE_PATH))
                .body(contact)
                .contentType(ContentType.JSON).when()
                .post().then().assertThat().statusCode(200).extract().path("message");
        id = IDExtractor.getID(message);
        System.out.println("new contact has been created");
    }

    @Test
    public void updateContactPositive(){

        contact.setId(id);
        contact.setEmail(EmailGenerator.generateEmail(3,3,3));

        given()
                .header(AUTHORIZATION_HEADER, PropertiesReaderXML.getProperty("token",XML_FILE_PATH))
                .body(contact).contentType(ContentType.JSON)
                .when().put().then()
                .assertThat()
                .body("message", containsString("updated"));

    }

    @Test
    public void updateContactNegative(){

        contact.setId("incorrect-id-4545-a115-7da39da099e0");
        contact.setEmail(EmailGenerator.generateEmail(3,3,3));

        given()
                .header(AUTHORIZATION_HEADER, PropertiesReaderXML.getProperty("token",XML_FILE_PATH))
                .body(contact).contentType(ContentType.JSON)
                .when().put().then().log().all()
                .assertThat()
                .body("message", containsString("not found"));

    }


    @Test
    public void updateContactNegative1(){

        contact.setId("incorrect-id-4545-a115-7da39da099e0");
        contact.setEmail(PhoneNumberGenerator.generatePhoneNumber());

        given()
                .header(AUTHORIZATION_HEADER, PropertiesReaderXML.getProperty("token",XML_FILE_PATH))
                .body(contact).contentType(ContentType.JSON)
                .when().put().then()
                .assertThat()
                .body("error", containsString("Bad Request"));

    }
}