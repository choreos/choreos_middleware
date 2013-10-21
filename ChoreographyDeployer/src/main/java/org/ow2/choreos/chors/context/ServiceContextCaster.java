package org.ow2.choreos.chors.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.LegacyService;
import org.ow2.choreos.chors.datamodel.LegacyServiceInstance;
import org.ow2.choreos.invoker.Invoker;
import org.ow2.choreos.invoker.InvokerException;
import org.ow2.choreos.invoker.InvokerFactory;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.Proxification;
import org.ow2.choreos.services.datamodel.Service;
import org.ow2.choreos.services.datamodel.ServiceDependency;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceType;

/**
 * Cast choreography context to a single service.
 * 
 * @author leonardo
 * 
 */
public class ServiceContextCaster {

    private static final String TASK_NAME = "SET_INVOCATION_ADDRESS";

    private static Logger logger = Logger.getLogger(ServiceContextCaster.class);

    private final Choreography chor;
    private final Service consumer;
    private final String consumerName;

    private Service provider;
    private String providerName, providerRole;

    public ServiceContextCaster(Choreography chor, Service consumer) {
        this.chor = chor;
        this.consumer = consumer;
        this.consumerName = consumer.getSpec().getName();
    }

    public void cast() {

        if (!isConsumerOK())
            return;

        Map<String, Service> deployedServicesMap = chor.getMapOfServicesBySpecNames();
        for (ServiceDependency dep : consumer.getSpec().getDependencies()) {
            providerName = dep.getServiceSpecName();
            providerRole = dep.getServiceSpecRole();
            provider = deployedServicesMap.get(providerName);
            if (provider == null) {
                logger.error("Service " + providerName + " not deployed. Not goint to pass this context to "
                        + consumerName + " on chor " + chor.getId());
            } else {
                if (provider.getSpec().getRoles().contains(providerRole))
                    sendProviderContextToConsumer();
                else
                    logger.error("Service " + providerName + " does not have role " + providerRole
                            + "; not going to pass it to service " + consumerName + " on chor " + chor.getId());
            }
        }
    }

    private boolean isConsumerOK() {
        List<ServiceDependency> dependencies = consumer.getSpec().getDependencies();
        return dependencies != null && !dependencies.isEmpty();
    }

    public void sendProviderContextToConsumer() {
        ServiceType consumerType = consumer.getSpec().getServiceType();
        List<String> consumerUris = consumer.getUris();
        List<String> providerUris = this.getUris(provider);
        for (String consumerEndpoint : consumerUris) {
            ContextSenderTask task = new ContextSenderTask(consumerType, consumerEndpoint, providerRole, providerName,
                    providerUris);
            InvokerFactory<Void> factory = new InvokerFactory<Void>();
            Invoker<Void> invoker = factory.geNewInvokerInstance(TASK_NAME, task);
            try {
                invoker.invoke();
            } catch (InvokerException e) {
                logger.error("Could not set " + providerName + " as " + providerRole + " to " + consumerName
                        + " on chor " + chor.getId());
            }
        }
    }

    /**
     * Get URIs from service that will be used in the setInvocationAddress.
     * 
     * Implementation: if possible, uses the SOAP bus URI; if not use the
     * nativeUri
     * 
     * @param providerService
     * @return
     */
    private List<String> getUris(Service service) {
        if (service instanceof DeployableService) {
            return getUrisFromDeployable((DeployableService) service);
        } else if (service instanceof LegacyService) {
            return getUrisFromLegacy((LegacyService) service);
        }
        throw new IllegalArgumentException("Invalid service " + service);
    }

    private List<String> getUrisFromDeployable(DeployableService providerService) {
        List<String> uris = new ArrayList<String>();
        for (ServiceInstance instance : providerService.getInstances()) {
            Proxification prox = instance.getProxification();
            boolean isProxified = prox != null && prox.getBusUri(ServiceType.SOAP) != null;
            if (isProxified) {
                String proxifiedUri = prox.getBusUri(ServiceType.SOAP);
                uris.add(proxifiedUri);
            } else {
                uris.add(instance.getNativeUri());
            }
        }
        return uris;
    }

    // TODO: eliminate replication by building an abstract ServiceInstance

    private List<String> getUrisFromLegacy(LegacyService providerService) {
        List<String> uris = new ArrayList<String>();
        for (LegacyServiceInstance instance : providerService.getLegacyServiceInstances()) {
            Proxification prox = instance.getProxification();
            boolean isProxified = prox != null && prox.getBusUri(ServiceType.SOAP) != null;
            if (isProxified) {
                String proxifiedUri = prox.getBusUri(ServiceType.SOAP);
                uris.add(proxifiedUri);
            } else {
                uris.add(instance.getUri());
            }
        }
        return uris;
    }

    private class ContextSenderTask implements Callable<Void> {

        ServiceType consumerType;
        String consumerEndpoint, providerRole, providerName;
        List<String> providerEndpoints;

        public ContextSenderTask(ServiceType consumerType, String consumerEndpoint, String providerRole,
                String providerName, List<String> providerEndpoints) {
            this.consumerType = consumerType;
            this.consumerEndpoint = consumerEndpoint;
            this.providerRole = providerRole;
            this.providerName = providerName;
            this.providerEndpoints = providerEndpoints;
        }

        @Override
        public Void call() throws Exception {
            ContextSender sender = ContextSenderFactory.getNewInstance(consumerType);
            sender.sendContext(consumerEndpoint, providerRole, providerName, providerEndpoints);
            return null;
        }
    }

}
