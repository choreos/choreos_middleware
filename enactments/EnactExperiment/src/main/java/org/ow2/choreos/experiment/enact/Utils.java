package org.ow2.choreos.experiment.enact;

import java.util.Calendar;
import java.util.Date;

public class Utils {

	private static Calendar cal = Calendar.getInstance(); 
	
	public static String getTimeStamp() {
		
		Date now = new Date();
		cal.setTime(now);
		String timeStamp = cal.get(Calendar.HOUR) + ":"
				+ cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
		
		return "[" + timeStamp + "] ";
	}
}
