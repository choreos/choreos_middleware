package br.usp.ime.ccsl.choreos.middleware.proxy;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class TestingAppender extends AppenderSkeleton {

    // We are collecting all the log messages in to a list
    private static List<String> messages = new ArrayList<String>();

    // This method is called when ever any logging happens
    // We are simply taking the log message and adding to our list
    @Override
    protected void append(LoggingEvent event) {
	messages.add(event.getRenderedMessage());
    }

    // This method is called when the appender is closed.
    // Gives an opportunity to clean up resources
    public void close() {
	messages.clear();
    }

    public boolean requiresLayout() {
	return false;
    }

    public static List<String> getMessages() {
	return messages;
    }

    public static void clear() {
	messages.clear();
    }
}