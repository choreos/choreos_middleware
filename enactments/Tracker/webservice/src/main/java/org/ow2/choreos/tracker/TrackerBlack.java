package org.ow2.choreos.tracker;

import java.net.MalformedURLException;
import java.util.concurrent.Callable;

import javax.jws.WebService;

import org.ow2.choreos.breaker.Invoker;
import org.ow2.choreos.breaker.InvokerBuilder;
import org.ow2.choreos.breaker.InvokerException;

@WebService(endpointInterface = "org.ow2.choreos.tracker.Tracker")
public class TrackerBlack extends AbstractTracker {

	@Override
	protected void updateMyId(final int targetId) {
		id = targetId + 1;
		setTargetId();
	}

	private void setTargetId() {
		final SetDependencyIdTask task = new SetDependencyIdTask();
		final Invoker<Void> invoker = new InvokerBuilder<Void>(task, 15)
				.trials(7).pauseBetweenTrials(10).build();
		try {
			invoker.invoke();
		} catch (InvokerException e) {
			// Could not help my neighbour =(
		}
	}

	private class SetDependencyIdTask implements Callable<Void> {
		@Override
		public Void call() throws MalformedURLException {
			final ProxyCreator proxyCreator = new ProxyCreator();
			final String targetWsdl = targets.values().iterator().next();
			final Tracker target = proxyCreator.getProxy(targetWsdl,
					TrackerType.WHITE);
			final int targetId = id - 1;
			target.setId(targetId);
			return null;
		}
	}
}
