package org.ow2.choreos.tracker;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;

import javax.jws.WebService;

@WebService(endpointInterface = "org.ow2.choreos.tracker.Tracker")
public class TrackerWhite extends AbstractTracker {

    private transient TrackerType secondTargetType;

    @Override
    public void setInvocationAddress(final String role, final String name, final List<String> endpoints) {
        super.setInvocationAddress(role, name, endpoints);
        if (targets.size() == 2) {
            secondTargetType = TrackerType.valueOf(role);
        }
    }

    @Override
    protected String getTargetPathIds() throws MalformedURLException {
        final StringBuffer targetPathIds = new StringBuffer();
        final Iterator<String> iterator = targets.values().iterator();

        targetPathIds.append(super.getOneTargetPathIds(iterator.next(), TrackerType.WHITE));

        if (iterator.hasNext()) {
            targetPathIds.append(super.getOneTargetPathIds(iterator.next(), secondTargetType));
        }

        return targetPathIds.toString();
    }
}
