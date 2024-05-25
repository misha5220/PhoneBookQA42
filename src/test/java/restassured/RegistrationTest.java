package restassured;

import helpers.EmailGenerator;
import helpers.Logger;
import helpers.PasswordStringGenerator;
import interfaces.TestHelper;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import models.AuthenticationRequestModel;
import models.AuthenticationResponseModel;
import models.ErrorModel;
import org.hamcrest.Matcher;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
public class RegistrationTest implements TestHelper {

    @Test
    public void testRegistrationPositive() throws IOException {

        Logger.setupLogger("src/logs/"+ this.getClass().getName()+".log" );


        AuthenticationRequestModel authenticationRequestModel = AuthenticationRequestModel.username(EmailGenerator.generateEmail(3,3,3)).password(PasswordStringGenerator.generatePassword());

        AuthenticationResponseModel responseModel = given()
                .body(authenticationRequestModel).contentType(ContentType.JSON)
                .post(BASE_URL+REGISTRATION_PATH)
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().as(AuthenticationResponseModel.class);

        System.out.println("The user " + authenticationRequestModel.getUsername() + "has been registered");

        Logger.closeLogger();
    }


    @Test
    @Description("Registration negative")
    public void testRegistrationNegative() throws IOException {
        AuthenticationRequestModel authenticationRequestModel = AuthenticationRequestModel.username(PasswordStringGenerator.generatePassword()).password(PasswordStringGenerator.generatePassword());

        ErrorModel errorModel = given()
                .body(authenticationRequestModel).contentType(ContentType.JSON)
                .post(BASE_URL+REGISTRATION_PATH)
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message.username",containsString("well-formed"))
                .extract().as(ErrorModel.class);


        System.out.println(errorModel.getMessage());

    }
}
