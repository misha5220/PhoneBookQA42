package helpers;

import javax.imageio.IIOException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {
    public static void main(String[] args) {
        System.out.println(getProperty("myuser"));
    }
    private static final String PROPERTIES_FILE="src/main/resources/resources.properties";

    public static String getProperty(String key){
        Properties properties = new Properties();
        try(FileInputStream fileInputStream = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(fileInputStream);
            return properties.getProperty(key);

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
