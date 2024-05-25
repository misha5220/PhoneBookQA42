package okhttp;

import helpers.EmailGenerator;
import helpers.PasswordStringGenerator;
import interfaces.TestHelper;
import models.AuthenticationResponseModel;
import models.ErrorModel;
import models.NewUserModel;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegistrationTest implements TestHelper {

    @Test
    public void registrationTest() throws IOException {

        NewUserModel newUserModel = new NewUserModel(EmailGenerator.generateEmail(3,3,2), PasswordStringGenerator.generatePassword());
        System.out.println(newUserModel);
        RequestBody requestBody = RequestBody.create(GSON.toJson(newUserModel),JSON);
        Request request = new Request.Builder().url(BASE_URL+REGISTRATION_PATH).post(requestBody).build();
        Response response = CLIENT.newCall(request).execute();
        String res = response.body().string();
        System.out.println(response.isSuccessful());
        if (response.isSuccessful()){
            AuthenticationResponseModel responseModel =  GSON.fromJson(res,AuthenticationResponseModel.class);
            String resultToken=responseModel.getToken();
            Assert.assertTrue(response.isSuccessful());
        }else {
            ErrorModel errorModel = GSON.fromJson(res, ErrorModel.class);
            System.out.println(errorModel.getMessage());
        }

    }

    @Test
    public void registrationTestNegativeEmail() throws IOException {

        NewUserModel newUserModel = new NewUserModel("incorrect@email.", PasswordStringGenerator.generatePassword());
        System.out.println(newUserModel);
        RequestBody requestBody = RequestBody.create(GSON.toJson(newUserModel),JSON);
        Request request = new Request.Builder().url(BASE_URL+REGISTRATION_PATH).post(requestBody).build();
        Response response = CLIENT.newCall(request).execute();
        String res = response.body().string();
        System.out.println(response.code());
        if (!response.isSuccessful()){
            ErrorModel errorModel = GSON.fromJson(res, ErrorModel.class);
            System.out.println(errorModel.getMessage());
            Assert.assertEquals(response.code(),400);
        }else {
            AuthenticationResponseModel responseModel =  GSON.fromJson(res,AuthenticationResponseModel.class);
            String resultToken=responseModel.getToken();
            Assert.assertEquals(response.code(),400);
        }

    }

    @Test
    public void registrationTestNegativeHugeEmail() throws IOException {

        NewUserModel newUserModel = new NewUserModel(EmailGenerator.generateEmail(50,72,108), PasswordStringGenerator.generatePassword());
        System.out.println(newUserModel);
        RequestBody requestBody = RequestBody.create(GSON.toJson(newUserModel),JSON);
        Request request = new Request.Builder().url(BASE_URL+REGISTRATION_PATH).post(requestBody).build();
        Response response = CLIENT.newCall(request).execute();
        String res = response.body().string();
        System.out.println(response.code());
        if (!response.isSuccessful()){
            ErrorModel errorModel = GSON.fromJson(res, ErrorModel.class);
            System.out.println(errorModel.getMessage());
            Assert.assertEquals(response.code(),400);
        }else {
            AuthenticationResponseModel responseModel =  GSON.fromJson(res,AuthenticationResponseModel.class);
            String resultToken=responseModel.getToken();
            Assert.assertEquals(response.code(),400);
        }

    }
    @Test
    public void registrationTestNegativeLowerCasePassword() throws IOException {

        NewUserModel newUserModel = new NewUserModel(EmailGenerator.generateEmail(3,3,2), "sdsadsadsadsa");
        System.out.println(newUserModel);
        RequestBody requestBody = RequestBody.create(GSON.toJson(newUserModel),JSON);
        Request request = new Request.Builder().url(BASE_URL+REGISTRATION_PATH).post(requestBody).build();
        Response response = CLIENT.newCall(request).execute();
        String res = response.body().string();
        System.out.println(response.code());
        if (!response.isSuccessful()){
            ErrorModel errorModel = GSON.fromJson(res, ErrorModel.class);
            System.out.println(errorModel.getMessage());
            Assert.assertEquals(response.code(),400);
        }else {
            AuthenticationResponseModel responseModel =  GSON.fromJson(res,AuthenticationResponseModel.class);
            String resultToken=responseModel.getToken();
            Assert.assertEquals(response.code(),400);
        }

    }

    @Test
    public void registrationTestNegativeShortPassword() throws IOException {

        NewUserModel newUserModel = new NewUserModel(EmailGenerator.generateEmail(3,3,2), "dsAS12@");
        System.out.println(newUserModel);
        RequestBody requestBody = RequestBody.create(GSON.toJson(newUserModel),JSON);
        Request request = new Request.Builder().url(BASE_URL+REGISTRATION_PATH).post(requestBody).build();
        Response response = CLIENT.newCall(request).execute();
        String res = response.body().string();
        System.out.println(response.code());
        if (!response.isSuccessful()){
            ErrorModel errorModel = GSON.fromJson(res, ErrorModel.class);
            System.out.println(errorModel.getMessage());
            Assert.assertEquals(response.code(),400);
        }else {
            AuthenticationResponseModel responseModel =  GSON.fromJson(res,AuthenticationResponseModel.class);
            String resultToken=responseModel.getToken();
            Assert.assertEquals(response.code(),400);
        }

    }

    @Test
    public void registrationTestNegativeExistingUser() throws IOException {

        NewUserModel newUserModel = new NewUserModel(EmailGenerator.generateEmail(3,3,2), PasswordStringGenerator.generatePassword());
        System.out.println(newUserModel);
        RequestBody requestBody = RequestBody.create(GSON.toJson(newUserModel),JSON);
        Request request = new Request.Builder().url(BASE_URL+REGISTRATION_PATH).post(requestBody).build();
        Response response = CLIENT.newCall(request).execute();
        String res = response.body().string();
        System.out.println(response.code());
        Assert.assertEquals(response.code(),200);
        System.out.println(newUserModel);
        response = CLIENT.newCall(request).execute();
        res = response.body().string();
        System.out.println(response.code());
        if(!response.isSuccessful()) {
            ErrorModel errorModel = GSON.fromJson(res, ErrorModel.class);
            System.out.println(errorModel.getMessage());
            Assert.assertEquals(response.code(), 409);
        } else {
            AuthenticationResponseModel responseModel =  GSON.fromJson(res,AuthenticationResponseModel.class);
            String resultToken=responseModel.getToken();
            Assert.assertEquals(response.code(),409);
        }

    }

}
