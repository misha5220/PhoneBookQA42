package okhttp;

import helpers.PropertiesReaderXML;
import helpers.PropertiesWriterXML;
import interfaces.TestHelper;
import models.AuthenticationRequestModel;
import models.AuthenticationResponseModel;
import models.ErrorModel;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest implements TestHelper {

    @Test
    public void loginPositive() throws IOException {
        AuthenticationRequestModel requestModel = AuthenticationRequestModel
                .username(PropertiesReaderXML.getProperty("user1",XML_FILE_PATH))
                .password(PropertiesReaderXML.getProperty("mypassword",XML_FILE_PATH));
        RequestBody requestBody = RequestBody.create(GSON.toJson(requestModel), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_PATH)
                .post(requestBody)
                .build();
        Response response = CLIENT.newCall(request).execute();
        System.out.println("Code response : "+response.code());
        if (response.isSuccessful()){
            AuthenticationResponseModel responseModel =
                    GSON.fromJson(response.body().string(), AuthenticationResponseModel.class);
            PropertiesWriterXML propertiesWriterXML = new PropertiesWriterXML();
            propertiesWriterXML.setProperty(TOKEN,responseModel.getToken(),false, XML_FILE_PATH);
            Assert.assertTrue(response.isSuccessful());
        }else {
            System.out.println("Status code : "+response.code());
            ErrorModel errorModel = GSON.fromJson(response.body().string(), ErrorModel.class);
            System.out.println("Error status : "+errorModel.getStatus());
        }

    }

    @Test
    public void loginNegative() throws IOException {
        AuthenticationRequestModel requestModel = AuthenticationRequestModel
                .username(PropertiesReaderXML.getProperty("user1",XML_FILE_PATH))
                .password("FakePassWord46548");
        System.out.println("Request model : "+requestModel.getUsername()+" : "+requestModel.getPassword());
        RequestBody requestBody = RequestBody.create(GSON.toJson(requestModel), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_PATH)
                .post(requestBody)
                .build();
        System.out.println("Request : "+request.toString());
        Response response = CLIENT.newCall(request).execute();
        System.out.println("Code response : "+response.code());
        if (!response.isSuccessful()){
            System.out.println("Status code : " + response.code());
            ErrorModel errorModel = GSON.fromJson(response.body().string(), ErrorModel.class);
            System.out.println("Error status : " + errorModel.getStatus());
            Assert.assertEquals(response.code(), 401);
        }else {
            System.out.println("dsadsda");
        }

    }


}