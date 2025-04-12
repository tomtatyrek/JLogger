import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Simple Logger class for Java
 * @version 2.0
 */
public class JLogger {


    private File logFile;
    private BufferedWriter writer;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss:SSS");
    private LocalDateTime now;

    /**
     * Returns JLogger object
     * @param logPath Path to a folder in which log files are stored
     */
    public JLogger(String logPath){

        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy_MM_dd-HH_mm_ss");
        now = LocalDateTime.now();
        try {
            File logFile = new File(logPath + "/log_" + dtf2.format(now) + ".txt");
            if (logFile.createNewFile()) {
                writer = new BufferedWriter(new FileWriter(logFile.getPath()));
                DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                writer.write("New log file created successfully on " + dtf3.format(now) + " :");
                writer.flush();
                System.out.println("New log created successfully on " + dtf3.format(now) + " :");
            }
        } catch (IOException ignored) {

        }

    }

    public void fatal(String s) {

        try {
            now = LocalDateTime.now();
            writer.write("\n[" + dtf.format(now) + "] [FATAL/" + getCallerClassName() + "] " + s);
            writer.flush();
            System.out.println("[" + dtf.format(now) + "] [FATAL/" + getCallerClassName() + "] " + s);
        } catch (IOException ignored) {

        }

    }
    public void error(String s) {

        try {
            now = LocalDateTime.now();
            writer.write("\n[" + dtf.format(now) + "] [ERROR/" + getCallerClassName() + "] " + s);
            writer.flush();
            System.out.println("[" + dtf.format(now) + "] [ERROR/" + getCallerClassName() + "] " + s);
        } catch (IOException ignored) {

        }

    }
    public void warning(String s) {

        try {
            now = LocalDateTime.now();
            writer.write("\n[" + dtf.format(now) + "] [WARNING/" + getCallerClassName() + "] " + s);
            writer.flush();
            System.out.println("[" + dtf.format(now) + "] [WARNING/" + getCallerClassName() + "] " + s);
        } catch (IOException ignored) {

        }

    }
    public void info(String s) {

        try {
            now = LocalDateTime.now();
            writer.write("\n[" + dtf.format(now) + "] [INFO/" + getCallerClassName() + "] " + s);
            writer.flush();
            System.out.println("[" + dtf.format(now) + "] [INFO/" + getCallerClassName() + "] " + s);
        } catch (IOException ignored) {

        }

    }
    public void debug(String s) {

        try {
            now = LocalDateTime.now();
            writer.write("\n[" + dtf.format(now) + "] [DEBUG/" + getCallerClassName() + "] " + s);
            writer.flush();
            System.out.println("[" + dtf.format(now) + "] [DEBUG/" + getCallerClassName() + "] " + s);
        } catch (IOException ignored) {

        }

    }
    public void trace(String s) {

        try {
            now = LocalDateTime.now();
            writer.write("\n[" + dtf.format(now) + "] [TRACE/" + getCallerClassName() + "] " + s);
            writer.flush();
            System.out.println("[" + dtf.format(now) + "] [TRACE/" + getCallerClassName() + "] " + s);
        } catch (IOException ignored) {

        }

    }

    private static String getCallerClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(JLogger.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
                return ste.getClassName();
            }
        }
        return null;
    }

}
