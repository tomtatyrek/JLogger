package io.github.tomtatyrek.jlogger;

import java.io.*;
import java.nio.file.FileSystems;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * An instance of this class is used for simple logging to the console and/or to a file.
 *
 * @version 1.0.0
 * @author Tomáš Tatyrek
 */
public class JLogger {

    //====STATIC CONSTANTS====
    private static final String DEFAULT_LOG_DTF_PATTERN = "HH:mm:ss.SSS";
    private static final String DEFAULT_FILENAME_DTF_PATTERN = "yyyy_MM_dd-HH_mm_ss";

    //====PRIVATE FIELDS====
    private PrintStream consoleOutputStream = null;
    private BufferedWriter fileWriter = null;
    private final DateTimeFormatter logDTF;
    private DateTimeFormatter filenameDTF = null;

    //====CONSTRUCTORS====

    /**
     * Returns a JLogger object, which logs to console and to a file, using specified {@link java.time.format.DateTimeFormatter DateTimeFormatter} patterns.
     *
     * @param logPath A string path to your log file <b>OR</b> the folder in which you want to keep automatically created log files.
     * @param filenameDTFPattern A {@link java.time.format.DateTimeFormatter DateTimeFormatter} string pattern for the format of date and time in the automatically created log files.
     * @param consoleStream A {@link java.io.PrintStream PrintStream} to which console log messages will be printed <i>e.g. System.out</i>
     * @param logDTFPattern A {@link java.time.format.DateTimeFormatter DateTimeFormatter} string pattern for the format of date and time shown in the log messages.
     * @throws IOException if there was an error while creating or wtiting to the specified log file or log folder
     */
    public JLogger(String logPath, String filenameDTFPattern, PrintStream consoleStream, String logDTFPattern) throws IOException {

        //Initializes log DateTimeFormater field
        logDTF = DateTimeFormatter.ofPattern(logDTFPattern);
        filenameDTF = DateTimeFormatter.ofPattern(filenameDTFPattern);

        setupFileWriter(logPath);

        consoleOutputStream = consoleStream;

    }

    /**
     * Returns a JLogger object, which logs only to a file, using specified {@link java.time.format.DateTimeFormatter DateTimeFormatter} patterns.
     *
     * @param logPath A string path to your log file <b>OR</b> the folder in which you want to keep automatically created log files.
     * @param filenameDTFPattern A {@link java.time.format.DateTimeFormatter DateTimeFormatter} string pattern for the format of date and time in the automatically created log files.
     * @param logDTFPattern A {@link java.time.format.DateTimeFormatter DateTimeFormatter} string pattern for the format of date and time shown in the log messages.
     * @throws IOException if there was an error while creating or wtiting to the specified log file or log folder
     */
    public JLogger(String logPath, String filenameDTFPattern, String logDTFPattern) throws IOException {

        //Initializes log DateTimeFormater field
        logDTF = DateTimeFormatter.ofPattern(logDTFPattern);
        filenameDTF = DateTimeFormatter.ofPattern(filenameDTFPattern);

        setupFileWriter(logPath);

    }

    /**
     * Returns a JLogger object, which logs only to a file, using specified {@link java.time.format.DateTimeFormatter DateTimeFormatter} pattern in log messages and the default {@link java.time.format.DateTimeFormatter DateTimeFormatter} pattern in file names.
     *
     * @param logPath A string path to your log file <b>OR</b> the folder in which you want to keep automatically created log files.
     * @param logDTFPattern A {@link java.time.format.DateTimeFormatter DateTimeFormatter} string pattern for the format of date and time shown in the log messages.
     * @throws IOException if there was an error while creating or wtiting to the specified log file or log folder
     */
    public JLogger(String logPath, String logDTFPattern) throws IOException {

        //Initializes log DateTimeFormater field
        logDTF = DateTimeFormatter.ofPattern(logDTFPattern);
        filenameDTF = DateTimeFormatter.ofPattern(DEFAULT_FILENAME_DTF_PATTERN);

        setupFileWriter(logPath);

    }

    /**
     * Returns a JLogger object, which logs to console and to a file, using default {@link java.time.format.DateTimeFormatter DateTimeFormatter} pattern.
     *
     * @param logPath A string path to your log file <b>OR</b> the folder in which you want to keep automatically created log files.
     * @param consoleStream A {@link java.io.PrintStream PrintStream} to which console log messages will be printed <i>e.g. System.out</i>
     * @throws IOException if there was an error while creating or wtiting to the specified log file or log folder
     */
    public JLogger(String logPath, PrintStream consoleStream) throws IOException {

        //Initializes log DateTimeFormater field
        logDTF = DateTimeFormatter.ofPattern(JLogger.DEFAULT_LOG_DTF_PATTERN);
        filenameDTF = DateTimeFormatter.ofPattern(JLogger.DEFAULT_FILENAME_DTF_PATTERN);

        setupFileWriter(logPath);

        consoleOutputStream = consoleStream;

    }

    /**
     * Returns a JLogger object, which logs only to a file, using default {@link java.time.format.DateTimeFormatter DateTimeFormatter} patterns.
     *
     * @param logPath A string path to your log file <b>OR</b> the folder in which you want to keep automatically created log files.
     * @throws IOException if there was an error while creating or wtiting to the specified log file or log folder
     */
    public JLogger(String logPath) throws IOException {

        //Initializes log DateTimeFormater field
        logDTF = DateTimeFormatter.ofPattern(JLogger.DEFAULT_LOG_DTF_PATTERN);
        filenameDTF = DateTimeFormatter.ofPattern(JLogger.DEFAULT_FILENAME_DTF_PATTERN);

        setupFileWriter(logPath);

    }

    /**
     * Returns a JLogger object, which logs only to a console, using specified {@link java.time.format.DateTimeFormatter DateTimeFormatter} pattern.
     *
     * @param consoleStream A {@link java.io.PrintStream PrintStream} to which console log messages will be printed <i>e.g. System.out</i>
     * @param logDTFPattern A {@link java.time.format.DateTimeFormatter DateTimeFormatter} string pattern for the format of date and time shown in the log messages.
     */
    public JLogger(PrintStream consoleStream, String logDTFPattern) {

        logDTF = DateTimeFormatter.ofPattern(logDTFPattern);
        consoleOutputStream  = consoleStream;

    }

    /**
     * Returns a JLogger object, which logs only to a console, using default {@link java.time.format.DateTimeFormatter DateTimeFormatter} pattern.
     *
     * @param consoleStream A {@link java.io.PrintStream PrintStream} to which console log messages will be printed <i>e.g. System.out</i>
     */
    public JLogger(PrintStream consoleStream) {

        logDTF = DateTimeFormatter.ofPattern(DEFAULT_LOG_DTF_PATTERN);
        consoleOutputStream  = consoleStream;

    }

    /**
     * Returns default JLogger object, which logs only to <i>System.out</i>
     */
    public JLogger() {
        logDTF = DateTimeFormatter.ofPattern(JLogger.DEFAULT_LOG_DTF_PATTERN);
        consoleOutputStream = System.out;
    }
    //====PUBLIC METHODS====

    /**
     * Logs a <b>TRACE</b> message.
     *
     * @param message A String message to be logged.
     */
    public void trace(String message) {
        logSpecifiedType("TRACE", message);
    }

    /**
     * Logs a <b>DEBUG</b> message.
     *
     * @param message A String message to be logged.
     */
    public void debug(String message) {
        logSpecifiedType("DEBUG", message);
    }

    /**
     * Logs an <b>INFO</b> message.
     *
     * @param message A String message to be logged.
     */
    public void info(String message) {
        logSpecifiedType("INFO", message);
    }

    /**
     * Logs a <b>WARN</b> message.
     *
     * @param message A String message to be logged.
     */
    public void warn(String message) {
        logSpecifiedType("WARN", message);
    }

    /**
     * Logs an <b>ERROR</b> message.
     *
     * @param message A String message to be logged.
     */
    public void error(String message) {
        logSpecifiedType("ERROR", message);
    }

    /**
     * Logs a <b>FATAL</b> message.
     *
     * @param message A String message to be logged.
     */
    public void fatal(String message) {
        logSpecifiedType("FATAL", message);
    }

    //====PRIVATE METHODS====
    private void logSpecifiedType(String logType, String message) {
        if (fileWriter != null) {
            logSpecifiedTypeIntoFile(logType, message);
        }
        if (consoleOutputStream != null) {
            logSpecifiedTypeIntoConsole(logType, message);
        }
    }
    private void logSpecifiedTypeIntoConsole(String logType, String message) {
        final LocalDateTime now = LocalDateTime.now();
        consoleOutputStream.println("[" + logDTF.format(now) + "] [" + logType + "/" + getCallerClassName() + "] " + message);
    }
    private void logSpecifiedTypeIntoFile(String logType, String message) {
        try {
            final LocalDateTime now = LocalDateTime.now();
            fileWriter.write("[" + logDTF.format(now) + "] [" + logType + "/" + getCallerClassName() + "] " + message + getEOL());
            fileWriter.flush();
        } catch (IOException ignored) {

        }
    }

    private static String getCallerClassName() {
        final StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (int i=1; i<stackTraceElements.length; i++) {
            StackTraceElement ste = stackTraceElements[i];
            if (!ste.getClassName().equals(JLogger.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
                return ste.getClassName();
            }
        }
        return null;
    }

    private void setupFileWriter(String logPath) throws IOException {
        File file;
        if (logPath.endsWith(".txt") || logPath.endsWith(".log")) {
            // Executes if a file was provided
            file = new File(logPath);
        } else {
            // Executes if a folder was provided
            file = new File(logPath + getSeparatorChar() + "log_" + LocalDateTime.now().format(filenameDTF) + ".txt");
        }

        // Create parent directories if they don't exist
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Could not create directory " + parentDir.getAbsolutePath());
            }
        }

        if (!file.exists()) {
            if (!file.createNewFile()) {
                throw new IOException("Could not create file " + file.getAbsolutePath());
            }
        }

        fileWriter = new BufferedWriter(new FileWriter(file));
    }

    private static String getSeparatorChar() {
        return FileSystems.getDefault().getSeparator();
    }

    private static String getEOL() {
        return System.lineSeparator();
    }

    //====GETTERS====
    //====SETTERS====

}
