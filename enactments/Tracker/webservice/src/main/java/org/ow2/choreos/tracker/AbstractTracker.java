package org.ow2.choreos.tracker;

import java.net.MalformedURLException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.ow2.choreos.utils.LogConfigurator;

@SuppressWarnings("PMD.ShortVariable")
public abstract class AbstractTracker implements Tracker {

    protected transient int id = -1;

    // key is tracker id and value is tracker wsdl
    protected transient SortedMap<Integer, String> targets = new TreeMap<Integer, String>();

    private final static Logger LOG = Logger.getLogger(AbstractTracker.class);

    static {
        LogConfigurator.configLog();
    }

    @Override
    public void setInvocationAddress(final String role, final String name, final List<String> endpoints) {
        final int targetId = parseIdFromName(name);
        LOG.info("Receiving target " + name);
        if (!endpoints.isEmpty()) { // To ease tests
            targets.put(targetId, endpoints.get(0));
        }
        updateMyId(targetId);
    }

    protected void updateMyId(final int targetId) {
        if (id == -1 || id > targetId - 1) {
            id = targetId - 1;
        }
        LOG.info("My id now is " + id + " (due to setInvocationAddress)");
    }

    private int parseIdFromName(final String name) {
        final Pattern pattern = Pattern.compile("\\D+(\\d+)");
        final Matcher matcher = pattern.matcher(name);

        if (matcher.find() && matcher.groupCount() > 0) {
            return Integer.parseInt(matcher.group(1));
        } else {
            final String msg = "setInvocationAddress name must be \\D+\\d+";
            LOG.error(msg);
            throw new InvalidParameterException(msg);
        }
    }

    @Override
    public void setId(final int id) {
        this.id = id;
        LOG.info("My id now is " + id + " (dur to setId)");
    }

    @Override
    public String getPathIds() throws MalformedURLException {
        String pathIds;

        if (targets.isEmpty()) {
            pathIds = Integer.toString(id);
        } else {
            pathIds = Integer.toString(id) + getTargetPathIds();
        }

        LOG.info("Returned pathIds: " + pathIds);
        return pathIds;
    }

    abstract protected String getTargetPathIds() throws MalformedURLException;

    protected StringBuffer getOneTargetPathIds(final String wsdl, final TrackerType type) throws MalformedURLException {
        LOG.info("Invoking my friend " + wsdl);

        final StringBuffer pathIds = new StringBuffer();
        final ProxyCreator proxyCreator = new ProxyCreator();
        final Tracker target = proxyCreator.getProxy(wsdl, type);

        pathIds.append(' ');
        pathIds.append(target.getPathIds());

        return pathIds;
    }
}
