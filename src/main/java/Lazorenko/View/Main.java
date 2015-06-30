package Lazorenko.View;

import Lazorenko.Controller.Controller;
import Lazorenko.Logger.LoggerToFile;

/**
 * Created by Master on 23-May-15.
 */
public class Main {
    public static void main(String[] args) {
        LoggerToFile loggerToFile = LoggerToFile.getInstance();
        loggerToFile.getLogger().fatal("Application running");
        Controller controller = new Controller();
        controller.run();
    }
}


