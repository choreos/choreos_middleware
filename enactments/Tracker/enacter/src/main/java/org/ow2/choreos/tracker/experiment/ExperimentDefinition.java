package org.ow2.choreos.tracker.experiment;

public class ExperimentDefinition {
    
    private int run, chorsQty, chorsSize, vmLimit;

    public ExperimentDefinition(int run, int chorsQty, int chorsSize, int vmLimit) {
        this.run = run;
        this.chorsQty = chorsQty;
        this.chorsSize = chorsSize;
        this.vmLimit = vmLimit;
    }

    public int getRun() {
        return run;
    }

    public int getChorsQty() {
        return chorsQty;
    }

    public int getChorsSize() {
        return chorsSize;
    }

    public int getVmLimit() {
        return vmLimit;
    }

    @Override
    public String toString() {
        return "ExperimentDefinition [run=" + run + ", chorsQty=" + chorsQty + ", chorsSize=" + chorsSize
                + ", vmLimit=" + vmLimit + "]";
    }

}
