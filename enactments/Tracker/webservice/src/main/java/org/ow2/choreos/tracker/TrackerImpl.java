package org.ow2.choreos.tracker;

import java.net.MalformedURLException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebService;

@SuppressWarnings("PMD.ShortVariable")
@WebService(endpointInterface = "org.ow2.choreos.tracker.Tracker")
public class TrackerImpl implements Tracker {

	private transient int id = -1;
	private transient String targetWsdl = null;

	@Override
	public void setInvocationAddress(final String role, final String name,
			final List<String> endpoints) {
		if (!endpoints.isEmpty()) {
			targetWsdl = endpoints.get(0);
		}
		id = parseIdFromName(name) - 1;
	}

	private int parseIdFromName(final String name) {
		final Pattern pattern = Pattern.compile("\\D+(\\d+)");
		final Matcher matcher = pattern.matcher(name);

		if (matcher.find() && matcher.groupCount() > 0) {
			return Integer.parseInt(matcher.group(1));
		} else {
			throw new InvalidParameterException(
					"setInvocationAddress name must be \\D+\\d+");
		}
	}

	@Override
	public void setId(final int id) {
		this.id = id;
	}

	@Override
	public String getPathIds() throws MalformedURLException {
		String pathIds;

		if (targetWsdl == null) {
			pathIds = Integer.toString(id);
		} else {
			pathIds = Integer.toString(id) + " " + getTargetPathIds();
		}

		return pathIds;
	}

	private String getTargetPathIds() throws MalformedURLException {
		final ProxyCreator proxyCreator = new ProxyCreator();
		final Tracker target = proxyCreator.getProxy(targetWsdl);
		return target.getPathIds();
	}
}
