package org.ow2.choreos.invoker;

import java.util.concurrent.Callable;

import org.ow2.choreos.utils.TimeoutsAndTrials;

/**
 * Retrieves configuration from timeouts_and_trials
 * 
 * @author leonardo
 * 
 * @param <T>
 */
public class SimpleInvokerConfigurator<T> implements InvokerConfigurator<T> {

    private String taskName;
    private Callable<T> task;
    private InvokerBuilder<T> builder;

    @Override
    public InvokerBuilder<T> getConfiguredInvokerBuilder(String taskName, Callable<T> task) {
        this.taskName = taskName;
        this.task = task;
        initBuilder();
        setTrials();
        setPause();
        return builder;
    }

    private void initBuilder() {
        int timeout = TimeoutsAndTrials.get(taskName + "_TIMEOUT");
        builder = new InvokerBuilder<T>(taskName, task, timeout);
    }

    private void setTrials() {
        try {
            int trials = TimeoutsAndTrials.get(taskName + "_TRIALS");
            builder.trials(trials);
        } catch (IllegalArgumentException e) {
            // pas du problem: InvokerBuilder will use default value
        }
    }
    
    private void setPause() {
        try {
            int pause = TimeoutsAndTrials.get(taskName + "_PAUSE");
            builder.pauseBetweenTrials(pause);
        } catch (IllegalArgumentException e) {
            // pas du problem: InvokerBuilder will use default value
        }
    }

}
