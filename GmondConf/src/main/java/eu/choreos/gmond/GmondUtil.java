package eu.choreos.gmond;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GmondUtil {

	
	public static String readFile( String file ) throws IOException {
	    BufferedReader reader = new BufferedReader( new FileReader (file));
	    String line  = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line + "\n" );
	    }
	    return stringBuilder.toString();
	 }
}
