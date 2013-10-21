package org.ow2.choreos.invoker;

import java.util.concurrent.Callable;

public interface InvokerConfigurator<T> {

    public InvokerBuilder<T> getConfiguredInvokerBuilder(String taskName, Callable<T> task);

}
