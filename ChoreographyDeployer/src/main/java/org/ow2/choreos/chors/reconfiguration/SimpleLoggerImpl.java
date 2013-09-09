package org.ow2.choreos.chors.reconfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class SimpleLoggerImpl implements SimpleLogger {

    private static final String DEBUG = "DEBUG";
    private static final String INFO = "INFO";
    private static final String WARN = "WARN";
    private static final String ERROR = "ERROR";

    private File logFile;

    public SimpleLoggerImpl(String fileName) {

	logFile = new File(fileName);
	try {
	    logFile.createNewFile();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    public void info(String message) {
	this.writeMessage(INFO, message);
    }

    public void info(String message, Exception e) {
	this.info(message);
	this.writeMessage(INFO, e.getMessage());
    }

    public void debug(String message) {
	this.writeMessage(DEBUG, message);
    }

    public void debug(String message, Exception e) {
	this.debug(message);
	this.writeMessage(DEBUG, e.getMessage());
    }

    public void error(String message) {
	this.writeMessage(ERROR, message);
    }

    public void error(String message, Exception e) {
	this.error(message);
	this.writeMessage(ERROR, e.getMessage());
    }

    public void warn(String message) {
	this.writeMessage(WARN, message);
    }

    public void warn(String message, Exception e) {
	this.warn(message);
	this.writeMessage(WARN, e.getMessage());
    }

    private void writeMessage(String level, String message) {

	String entry = buildEntry(level, message);

	try {
	    FileWriter writer = new FileWriter(logFile, true);
	    BufferedWriter out = new BufferedWriter(writer);
	    out.append(entry);
	    out.close();
	    writer.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    private Calendar cal = Calendar.getInstance();

    private String buildEntry(String level, String message) {

	String timestamp = getTimestamp();
	String entry = "[" + level + ": " + timestamp + "] " + message + "\n";
	return entry;
    }

    private String getTimestamp() {

	Date now = new Date();
	cal.setTime(now);
	String timestamp = cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":"
		+ cal.get(Calendar.SECOND);
	return "" + System.currentTimeMillis();
	// return timestamp;
    }

}
