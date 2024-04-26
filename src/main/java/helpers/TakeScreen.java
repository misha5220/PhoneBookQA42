package helpers;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TakeScreen {
    public static void main(String[] args) {
        System.out.println("_"+System.currentTimeMillis()+".png");
    }

    @Attachment(value = "FailureTest", type = "image/png")
    public static byte[] takeScreenShot(WebDriver driver, String testName){
        Allure.step("Trying to take a screenshot.");
        try {
            String screenshotName = testName + "_" + System.currentTimeMillis() + ".png";// Создается строка screenshotName, которая содержит имя файла скриншота в формате имя_теста_время.png.
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE); // Затем вызывается метод getScreenshotAs у driver, который возвращает скриншот в виде объекта типа File.
            FileUtils.copyFile(screenshotFile, new File("screenshots/" + screenshotName)); // Полученный объект screenshotFile копируется в директорию screenshots с именем из screenshotName с помощью метода FileUtils.copyFile.
            return Files.readAllBytes(Paths.get("screenshots\\" + screenshotName)); // Скриншот считывается обратно в виде массива байтов
        } catch (IOException e) {
            System.out.println("sdasdsadsadasdsadasdsadsa");
            return null;
        }
    }
}
