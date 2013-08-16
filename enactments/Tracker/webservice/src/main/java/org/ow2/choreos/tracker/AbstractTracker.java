package org.ow2.choreos.tracker;

import java.net.MalformedURLException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebService;

@SuppressWarnings("PMD.ShortVariable")
@WebService(endpointInterface = "org.ow2.choreos.tracker.Tracker")
public abstract class AbstractTracker implements Tracker {

	protected transient int id = -1;

	// key is tracker id and value is tracker wsdl
	protected transient SortedMap<Integer, String> targets = new TreeMap<Integer, String>();

	@Override
	public void setInvocationAddress(final String role, final String name,
			final List<String> endpoints) {
		final int targetId = parseIdFromName(name);
		targets.put(targetId, endpoints.get(0));
		updateMyId();
	}

	protected abstract void updateMyId();

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

		if (targets.isEmpty()) {
			pathIds = Integer.toString(id);
		} else {
			pathIds = Integer.toString(id) + getTargetPathIds();
		}

		return pathIds;
	}

	private String getTargetPathIds() throws MalformedURLException {
		final StringBuffer targetPathIds = new StringBuffer();
		final ProxyCreator proxyCreator = new ProxyCreator();
		Tracker target;

		for (String wsdl : targets.values()) {
			targetPathIds.append(' ');
			target = proxyCreator.getProxy(wsdl);
			targetPathIds.append(target.getPathIds());
		}

		return targetPathIds.toString();
	}
}
