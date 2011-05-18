package br.usp.ime.ccsl.choreos.middleware.proxy;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for handling the XML Soap envelope
 * Obs: it's a package visibility class, since it's not used by the client
 * 
 * @author lucas, guilherme
 *
 */
class SoapEnvelopeHelper {

	private static final String REGEX_MATCH = ">\\?<";

	/**
	 * Fills the content of the XML Soap envelop with the given arguments
	 * @param xml
	 * @param parameters
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static String generate(String xml, List<String> parameters)
			throws IllegalArgumentException {

		for (int i = 0; i < parameters.size(); i++) {
			// Tag for the current argument
			if (xml.indexOf(">?<") < 0) {
				IllegalArgumentException noMoreParameters = new IllegalArgumentException(
						"Number of parameters exceeds number of parameters in XML envelope. Parameters expected: "
								+ (i - 1) + " parameters given: " + parameters.size());
				throw noMoreParameters;
			}
			xml = xml.replaceFirst(REGEX_MATCH, ">" + parameters.get(i) + "<");
			System.out.println(xml);
		}

		if (xml.indexOf(">?<") >= 0) {
			IllegalArgumentException lessParameters = new IllegalArgumentException(
					"Number of parameters less than number of parameters in XML envelope. "
							+ "Parameters given: " + parameters.size());
			throw lessParameters;

		}

		return xml;
	}

	/**
	 * Wrapper for generate(String, List)
	 * @param xml
	 * @param parameters
	 * @return
	 * 
	 * @see SoapEnvelopeHelper#generate(String, List)
	 */
	public static String generate(String xml, String... parameters)  {
		
		List<String> paramList = Arrays.asList(parameters);
		return generate(xml, paramList);
	}

	/**
	 * Retrieves the content data from XML Soap response
	 * @param xml
	 * @return
	 */
	public static String getCleanResponse(String xml) {
		String patternStr = ":Body>\\s*?<.*?>(.*)</.*?>\\s*?</.*?:Body>";
		// Compile and use regular expression
		Pattern pattern = Pattern.compile(patternStr, Pattern.DOTALL);
		Matcher matcher = pattern.matcher(xml);
		boolean matchFound = matcher.find();

		if (matchFound) {
			if (matcher.groupCount() > 0) {
				return matcher.group(1).trim();
			}
		}

		return xml;
	}

}
