package org.ow2.choreos.ee.bus;

import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.invoker.Invoker;
import org.ow2.choreos.invoker.InvokerException;
import org.ow2.choreos.invoker.InvokerFactory;

public class ServicesProxifier {

    private static final String TASK_NAME = "PROXIFY";

    private Choreography chor;
    private List<ProxificationTask> proxificationTasks;

    private Logger logger = Logger.getLogger(ServicesProxifier.class);

    public ServicesProxifier(Choreography chor) {
        this.chor = chor;
    }

    public void proxify() {
        logger.info("Resquested to proxify depoloyed services");
        selectEsbNodes();
        executeProxificationTasks();
        // TODO: should PUT /services/ (a registry would resolve...)
    }

    private void selectEsbNodes() {
        ESBNodesSelector selector = new ESBNodesSelector();
        proxificationTasks = selector.selectESBNodes(chor);
    }

    private void executeProxificationTasks() {
        for (ProxificationTask task : proxificationTasks) {
            String svcName = task.getSvcName();
            try {
                InvokerFactory<String> factory = new InvokerFactory<String>();
                Invoker<String> invoker = factory.geNewInvokerInstance(TASK_NAME, task);
                String proxifiedAddress = invoker.invoke();
                logger.info(svcName + " instance proxified (" + proxifiedAddress + ")");
            } catch (InvokerException e) {
                logger.error(svcName + " could not be proxified");
            }
        }
    }

}
