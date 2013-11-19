package org.ow2.choreos.ee.services.update;

public abstract class BaseAction implements UpdateAction {

    public abstract String getName();

    @Override
    public String toString() {
        return getName();
    }
    
}
