package org.ow2.choreos.experiment.enact;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.client.NodesClient;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;

public class Bootstrapper {

    private static final String NPM_HOST = "http://localhost:9100/deploymentmanager";

    private int vmsQuantity; // how many VMs we will use
    private Report report;

    public Bootstrapper(int vmsQuantity, Report report) {
        this.vmsQuantity = vmsQuantity;
        this.report = report;
    }

    private List<CloudNode> vms;

    /**
     * Creates VMs,
     */
    public void boot() {

        System.out.println(Utils.getTimeStamp() + "Creating VMs...");
        long t0 = System.currentTimeMillis();
        List<CloudNode> vms = createVMs();
        System.out.println(Utils.getTimeStamp() + "Created machines: ");
        for (CloudNode vm : vms) {
            String timeStamp = Utils.getTimeStamp();
            if (timeStamp != null & vm != null) {
                System.out.println(timeStamp + vm.getIp() + "  ");
            }
        }
        long tf = System.currentTimeMillis();
        long duration = tf - t0;
        System.out.println(Utils.getTimeStamp() + "### Bootstrap completed in " + duration + " milliseconds ###");
        report.setVmsCreationTotalTime(duration);
    }

    private List<CloudNode> createVMs() {

        final List<CloudNode> vms = new ArrayList<CloudNode>();
        Thread[] trds = new Thread[vmsQuantity];

        for (int i = 0; i < vmsQuantity; i++) {
            final int idx = i;
            trds[i] = new Thread(new Runnable() {
                @Override
                public void run() {

                    long t0 = System.currentTimeMillis();
                    NodePoolManager npm = new NodesClient(NPM_HOST);
                    NodeSpec nodeSpec = new NodeSpec();
                    CloudNode vm = null;
                    boolean created = false;

                    while (!created) {
                        try {
                            vm = npm.createNode(nodeSpec);
                            created = true;
                        } catch (NodeNotCreatedException e) {
                            System.out.println(Utils.getTimeStamp() + "VM #" + idx + " not created!");
                        }
                    }

                    long tf = System.currentTimeMillis();
                    long duration = tf - t0;
                    vms.add(vm);
                    System.out.println(Utils.getTimeStamp() + "VM #" + idx + " created in " + duration
                            + " milliseconds");
                    report.addVmCreationTime(duration);
                }
            });
            trds[i].start();
        }

        waitThreads(trds);

        return vms;
    }

    private void waitThreads(Thread[] trds) {
        for (Thread t : trds) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<CloudNode> getNodes() {
        return vms;
    }

    public static void main(String[] args) {
        Bootstrapper booter = new Bootstrapper(80, new Report(80));
        booter.boot();
    }
}
