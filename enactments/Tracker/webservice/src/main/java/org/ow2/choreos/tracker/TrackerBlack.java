package org.ow2.choreos.tracker;

import java.net.MalformedURLException;
import java.util.concurrent.Callable;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.ow2.choreos.invoker.Invoker;
import org.ow2.choreos.invoker.InvokerBuilder;
import org.ow2.choreos.invoker.InvokerException;

@WebService(endpointInterface = "org.ow2.choreos.tracker.Tracker")
public class TrackerBlack extends AbstractTracker {

    private static final Logger LOG = Logger.getLogger(TrackerBlack.class);

    @Override
    protected void updateMyId(final int targetId) {
        id = targetId + 1;
        LOG.info("My id now is " + id + " (due to setInvocationAddress)");
        setTargetId();
    }

    @Override
    protected String getTargetPathIds() throws MalformedURLException {
        final String firstTargetWsdl = targets.values().iterator().next();
        final StringBuffer firstTargetPath = super.getOneTargetPathIds(firstTargetWsdl, TrackerType.WHITE);
        return firstTargetPath.toString();
    }

    private void setTargetId() {
        final SetDependencyIdTask task = new SetDependencyIdTask();
        final Invoker<Void> invoker = new InvokerBuilder<Void>("SetDependencyIdTask", task, 15).trials(7)
                .pauseBetweenTrials(10).build();
        try {
            invoker.invoke();
        } catch (InvokerException e) {
            LOG.error("Could not set the id of my friend");
        }
    }

    private class SetDependencyIdTask implements Callable<Void> {
        @Override
        public Void call() throws MalformedURLException {
            final ProxyCreator proxyCreator = new ProxyCreator();
            final String targetWsdl = targets.values().iterator().next();
            final Tracker target = proxyCreator.getProxy(targetWsdl, TrackerType.WHITE);
            final int targetId = id - 1;
            LOG.info("Setting the id of my friend " + targetId);
            target.setId(targetId);
            return null;
        }
    }
}
