package org.ow2.choreos.deployment.services.update;

public abstract class BaseAction implements UpdateAction {

    public abstract String getName();

    @Override
    public String toString() {
        return getName();
    }
    
}
