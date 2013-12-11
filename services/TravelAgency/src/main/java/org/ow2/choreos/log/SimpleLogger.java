package org.ow2.choreos.log;

public interface SimpleLogger {
	
	public void debug(String message);
	
	public void debug(String message, Exception e);

	public void info(String message);

	public void info(String message, Exception e);

	public void warn(String message);

	public void warn(String message, Exception e);

	public void error(String message);

	public void error(String message, Exception e);

}
