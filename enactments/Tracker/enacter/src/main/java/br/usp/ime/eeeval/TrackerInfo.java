package br.usp.ime.eeeval;

import java.util.List;
import java.util.NoSuchElementException;

import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographyService;

public class TrackerInfo {

	public static final String ROLE = "tracker";
	private static final String SERVICE_NAME_PRE = "tracker";
	private transient Choreography chor = null;

	public void setChoreography(final Choreography chor) {
		this.chor = chor;
	}

	public String getName(final int trackerNumber) {
		return SERVICE_NAME_PRE + trackerNumber;
	}

	public String getWsdl(final int trackerNumber) {
		if (chor == null) {
			throw new IllegalStateException("Choreography not set.");
		}

		final String wanted = getName(trackerNumber);
		final List<ChoreographyService> chorServices = chor
				.getChoreographyServices();
		String current;

		for (ChoreographyService chorService : chorServices) {
			current = chorService.getChoreographyServiceSpec().getName();
			if (wanted.equals(current)) {
				return chorService.getService().getUris().get(0) + "?wsdl";
			}
		}

		throw new NoSuchElementException("Tracker not found: " + wanted);
	}

	public String getExpectedPathIds(final int chorSize) {
		final StringBuffer answer = new StringBuffer();
		for (int i = 0; i < chorSize; i++) {
			answer.append(i);
			answer.append(' ');
		}
		answer.deleteCharAt(answer.length() - 1);
		return answer.toString();
	}
}
