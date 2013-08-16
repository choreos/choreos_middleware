package org.ow2.choreos.tracker;

public class TrackerWhite extends AbstractTracker {

	@Override
	protected void updateMyId() {
		final int lowestTargetId = targets.firstKey();

		if (id == -1 || id > lowestTargetId - 1) {
			id = lowestTargetId - 1;
		}
	}
}
