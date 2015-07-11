package Lazorenko.Logger;

import org.apache.log4j.*;
import org.apache.log4j.helpers.DateLayout;
import org.apache.log4j.spi.LoggingEvent;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by andriylazorenko on 30.06.15.
 */
public class LoggerToFile {
    private static final String name = "main";
    private static final String path = "src/main/resources/log.txt";

    public Logger getLogger() {
        return logger;
    }

    private Logger logger = Logger.getLogger(name);

    private volatile static LoggerToFile uniqueInstance;

    public static LoggerToFile getInstance(){
        if (uniqueInstance==null){
            synchronized (LoggerToFile.class){
                if (uniqueInstance==null){
                    uniqueInstance = new LoggerToFile();
                }
            }
        }
        return uniqueInstance;
    }


    private LoggerToFile() {
        logger.setLevel(Level.INFO);
        Layout myLayout = new DateLayout() {

            @Override
            public String format(LoggingEvent event) {
                String forRet = "";
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/y hh:mm:ss");
                Date date = new Date();
                date.setTime(event.getTimeStamp());
                forRet += sdf.format(date)+" - ";
                forRet +=event.getLevel().toString()+" - ";
                forRet +=event.getMessage().toString();
                return forRet;
            }

            @Override
            public boolean ignoresThrowable() {
                return false;
            }
        };

        Appender appender = null;
        try {
            appender = new FileAppender(myLayout,path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        appender.setName(name);
        logger.addAppender(appender);
    }
}
