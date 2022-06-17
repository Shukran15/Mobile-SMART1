package ru.cleverence.mobilesmarts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {


    public String readParam(String param) {
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {

            Properties prop = new Properties();

            prop.load(input);

            return prop.getProperty(param);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return "";

    }


}
