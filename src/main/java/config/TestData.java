package config;

import helpers.EmailGenerator;
import org.testng.annotations.DataProvider;

public class TestData {

    @DataProvider(name = "loginData")
    public Object[][] loginData(){
    return new Object[][]{
            {EmailGenerator.generateEmail(4,3,3), "fakePassword"},
            {"fakeUser2", "fakePassword"}
        };
    }
}
