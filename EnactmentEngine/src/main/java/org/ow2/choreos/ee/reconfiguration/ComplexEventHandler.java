package org.ow2.choreos.ee.reconfiguration;

public abstract class ComplexEventHandler {

    protected ChoreographyRegistryHelper registryHelper = new ChoreographyRegistryHelper();

    public abstract void handleEvent(HandlingEvent event);

}
