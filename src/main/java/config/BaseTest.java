package config;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class BaseTest {

    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

@BeforeMethod
@Parameters("browser")
    public void setUp(@Optional ("chrome") String browser){

        if(browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--lang=en");

            driver=new ChromeDriver(options);

        } else if (browser.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options  = new FirefoxOptions();
            options.addPreference("intl.accept_languages","en");
            driver = new FirefoxDriver(options);

        }else{
            throw new IllegalArgumentException("invalid browser value" + browser);
        }

        driver = getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));





}

    @AfterMethod
    public void tearDown(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
        String currentDate = dateFormat.format(new Date());
        String fileName = "PhoneBook_"+currentDate+".log";

        try {
            FileWriter writer = new FileWriter(fileName);
            LogEntries logEntries = driver.manage().logs().get("server");
            for (LogEntry entry : logEntries){
                writer.write(entry.getMessage()+"\n");
            }   writer.close();
            System.out.println("File log has been created...");

        }catch (IOException e){}


        WebDriver driver1 = getDriver();
        if(driver1 != null){
            driver1.quit();
        }
    }

}
