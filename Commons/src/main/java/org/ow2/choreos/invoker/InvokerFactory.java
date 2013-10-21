package org.ow2.choreos.invoker;

import java.util.concurrent.Callable;

public class InvokerFactory<T> {

    public Invoker<T> geNewInvokerInstance(String taskName, Callable<T> task) {
        InvokerConfigurator<T> invokerConfigurator = new SimpleInvokerConfigurator<T>();
        InvokerBuilder<T> builder = invokerConfigurator.getConfiguredInvokerBuilder(taskName, task);
        return builder.build();
    }

}
