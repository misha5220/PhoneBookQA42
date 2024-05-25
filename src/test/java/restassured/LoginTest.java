package restassured;

import config.TestData;
import helpers.EmailGenerator;
import helpers.PasswordStringGenerator;
import helpers.PropertiesReaderXML;
import helpers.PropertiesWriterXML;
import interfaces.TestHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.AuthenticationRequestModel;
import models.AuthenticationResponseModel;
import models.ErrorModel;
import models.NewUserModel;
import org.testng.annotations.Test;

import java.io.IOException;
import static io.restassured.RestAssured.given;

public class LoginTest implements TestHelper {

    @Test
    public void registrationPositive() throws IOException {

        NewUserModel newUserModel = new NewUserModel(EmailGenerator.generateEmail(3, 3, 2), PasswordStringGenerator.generatePassword());
        String token = given().body(newUserModel).contentType(ContentType.JSON)
                .when().post(BASE_URL+REGISTRATION_PATH).then().assertThat().statusCode(200).extract().path("token");
        System.out.println("Token " + token);



    }

    @Test
    public void loginPositiveTest(){
        RestAssured.baseURI = BASE_URL+LOGIN_PATH;
        AuthenticationRequestModel requestModel = AuthenticationRequestModel
                .username(PropertiesReaderXML.getProperty("user1",XML_FILE_PATH))
                .password(PropertiesReaderXML.getProperty("mypassword",XML_FILE_PATH));
        AuthenticationResponseModel response = given().body(requestModel)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().as(AuthenticationResponseModel.class);
        System.out.println(response.getToken());

        PropertiesWriterXML propertiesWriterXML = new PropertiesWriterXML();
        propertiesWriterXML.setProperty(TOKEN,response.getToken(),false, XML_FILE_PATH);

    }

    @Test(dataProvider = "loginData",dataProviderClass = TestData.class)
    public void loginNegativeTest(String username, String password){
        RestAssured.baseURI = BASE_URL+LOGIN_PATH;
        AuthenticationRequestModel requestModel = AuthenticationRequestModel
                .username(username)
                .password(password);
        ErrorModel errorModel = given().body(requestModel)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then().log().all()
                .assertThat().statusCode(401)
                .extract().as(ErrorModel.class);



    }

}
