package io.github.tomtatyrek.jlogger;

import java.io.*;
import java.nio.file.FileSystems;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JLogger {

    //====STATIC CONSTANTS====
    public static final String DEFAULT_LOG_DTF_PATTERN = "HH:mm:ss.SSS";
    public static final String DEFAULT_FILENAME_DTF_PATTERN = "yyyy_MM_dd-HH_mm_ss";

    //====PRIVATE FIELDS====
    private PrintStream consoleOutputStream = null;
    private BufferedWriter fileWriter = null;
    private DateTimeFormatter logDTF;
    private DateTimeFormatter filenameDTF = null;

    //====CONSTRUCTORS====
    public JLogger(String logPath, String filenameDTFPattern, PrintStream consoleStream, String logDTFPattern) throws IOException {

        //Initializes log DateTimeFormater field
        logDTF = DateTimeFormatter.ofPattern(logDTFPattern);
        filenameDTF = DateTimeFormatter.ofPattern(filenameDTFPattern);

        setupFileWriter(logPath);

        consoleOutputStream = consoleStream;

    }

    public JLogger(String logPath, String filenameDTFPattern, String logDTFPattern) throws IOException {

        //Initializes log DateTimeFormater field
        logDTF = DateTimeFormatter.ofPattern(logDTFPattern);
        filenameDTF = DateTimeFormatter.ofPattern(filenameDTFPattern);

        setupFileWriter(logPath);

    }

    public JLogger(String logPath, PrintStream consoleStream) throws IOException {

        //Initializes log DateTimeFormater field
        logDTF = DateTimeFormatter.ofPattern(JLogger.DEFAULT_LOG_DTF_PATTERN);
        filenameDTF = DateTimeFormatter.ofPattern(JLogger.DEFAULT_FILENAME_DTF_PATTERN);

        setupFileWriter(logPath);

        consoleOutputStream = consoleStream;

    }

    public JLogger(String logPath) throws IOException {

        //Initializes log DateTimeFormater field
        logDTF = DateTimeFormatter.ofPattern(JLogger.DEFAULT_LOG_DTF_PATTERN);
        filenameDTF = DateTimeFormatter.ofPattern(JLogger.DEFAULT_FILENAME_DTF_PATTERN);

        setupFileWriter(logPath);

    }

    public JLogger(PrintStream consoleStream, String logDTFPattern) {

        logDTF = DateTimeFormatter.ofPattern(logDTFPattern);
        consoleOutputStream  = consoleStream;

    }

    public JLogger(PrintStream consoleStream) {

        logDTF = DateTimeFormatter.ofPattern(DEFAULT_LOG_DTF_PATTERN);
        consoleOutputStream  = consoleStream;

    }

    public JLogger() {
        logDTF = DateTimeFormatter.ofPattern(JLogger.DEFAULT_LOG_DTF_PATTERN);
        consoleOutputStream = System.out;
    }
    //====PUBLIC METHODS====
    public void trace(String message) {
        logSpecifiedType("TRACE", message);
    }
    public void debug(String message) {
        logSpecifiedType("DEBUG", message);
    }
    public void info(String message) {
        logSpecifiedType("INFO", message);
    }
    public void warn(String message) {
        logSpecifiedType("WARN", message);
    }
    public void error(String message) {
        logSpecifiedType("ERROR", message);
    }
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
