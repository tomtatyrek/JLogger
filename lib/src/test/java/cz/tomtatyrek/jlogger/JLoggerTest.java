package cz.tomtatyrek.jlogger;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class JLoggerTest {
    
    private static String sep() {
        return FileSystems.getDefault().getSeparator();
    }

    @Test
    public void basicConsoleTest() {
        JLogger jLogger = new JLogger();
        jLogger.info("Testing JLogger");
    }


    @Test
    public void basicFileTest() throws IOException {

        JLogger jLogger1 = new JLogger(System.getProperty("user.home") + sep() + ".tests" + sep() + "JLoggerTest");
        jLogger1.info("Testing JLogger");
        JLogger jLogger2 = new JLogger(System.getProperty("user.home") + sep() + ".tests" + sep() + "JLoggerTest" + sep() + "testing_log.txt");
        jLogger2.info("Testing JLogger");

    }

}
