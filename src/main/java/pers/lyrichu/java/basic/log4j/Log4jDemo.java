package pers.lyrichu.java.basic.log4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4jDemo {
    private static Logger LOGGER = LoggerFactory.getLogger(Log4jDemo.class);
    public static void main(String[] args) {
        LOGGER.debug("This is a debug message!");
        LOGGER.info("This is an info message!");
        LOGGER.warn("This is a warn message!");
        LOGGER.error("This is an error message!");
    }
}
