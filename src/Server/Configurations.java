package Server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configurations {

    public static String getProperty (String str) {
        try (InputStream input = Configurations.class.getClassLoader().getResourceAsStream("config.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                return null;
            }

            //load a properties file from class path, inside static method
            prop.load(input);
            return prop.getProperty(str);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
