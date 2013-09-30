package org.ow2.choreos.chors.bus;

import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.breaker.Invoker;
import org.ow2.choreos.breaker.InvokerBuilder;
import org.ow2.choreos.breaker.InvokerException;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.utils.TimeoutsAndTrials;

public class ServicesProxifier {

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
                Invoker<String> invoker = buildInvoker(task);
                String proxifiedAddress = invoker.invoke();
                logger.info(svcName + " instance proxified (" + proxifiedAddress + ")");
            } catch (InvokerException e) {
                logger.error(svcName + " could not be proxified");
            }
        }
    }

    private Invoker<String> buildInvoker(ProxificationTask task) {
        int timeout = TimeoutsAndTrials.get("PROXIFY_TIMEOUT");
        int trials = TimeoutsAndTrials.get("PROXIFY_TRIALS");
        int pause = TimeoutsAndTrials.get("PROXIFY_PAUSE");
        Invoker<String> invoker = new InvokerBuilder<String>(task, timeout).trials(trials).pauseBetweenTrials(pause)
                .build();
        return invoker;
    }

}
