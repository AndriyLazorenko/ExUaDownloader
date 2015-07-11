package Lazorenko.Model;

import Lazorenko.Logger.LoggerToFile;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by andriylazorenko on 30.06.15.
 */
public class Model {
    Properties properties = new Properties();
    LoggerToFile loggerToFile = LoggerToFile.getInstance();

    private volatile static Model uniqueInstance;

    private Model() {
        try {
            this.properties.load(new FileReader("src/main/resources/Properties.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            loggerToFile.getLogger().error(e.getMessage());
        }
    }

    public static Model getInstance(){
        if (uniqueInstance==null){
            synchronized (Model.class){
                if (uniqueInstance==null){
                    uniqueInstance = new Model();
                }
            }
        }
        return uniqueInstance;
    }

    public Properties getProperties() {
        return properties;
    }
}
