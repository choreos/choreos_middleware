package org.ow2.choreos.deployment.services.rest;

import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.services.ServicesManager;

public class ServicesResourceForTest extends ServicesResource {
		public ServicesResourceForTest(ServicesManager servicesManager, NodePoolManager npm) {
			super.servicesManager = servicesManager;
			super.npm = npm;
		}
}
