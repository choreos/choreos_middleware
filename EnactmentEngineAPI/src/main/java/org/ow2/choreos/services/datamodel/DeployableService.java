/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.ow2.choreos.nodes.datamodel.CloudNode;

@XmlRootElement
public class DeployableService extends Service {

    // selectedNodes and serviceInstances's nodes may not match, since instances
    // may be not deployed
    private List<CloudNode> selectedNodes;
    private List<ServiceInstance> serviceInstances;

    @XmlTransient
    private RecipeBundle recipeBundle;

    public DeployableService() {
        super();
    }

    public DeployableService(DeployableServiceSpec spec) {
        super(spec);
    }

    @Override
    public DeployableServiceSpec getSpec() {
        return (DeployableServiceSpec) super.getSpec();
    }

    @Override
    public void setSpec(ServiceSpec spec) {
        // This method seems to be necessary to JAXB set the super class
        // attribute when doing unmarshalling
        super.setSpec((DeployableServiceSpec) spec);
    }

    public List<ServiceInstance> getInstances() {
        return serviceInstances;
    }

    public void removeInstance(ServiceInstance instance) {
        if (serviceInstances != null)
            serviceInstances.remove(instance);
    }

    public List<ServiceInstance> getServiceInstances() {
        return serviceInstances;
    }

    public void setServiceInstances(List<ServiceInstance> instances) {
        this.serviceInstances = instances;
    }

    public synchronized void addInstance(ServiceInstance instance) {
        if (serviceInstances == null)
            serviceInstances = new ArrayList<ServiceInstance>();
        serviceInstances.add(instance);
    }

    public List<CloudNode> getSelectedNodes() {
        return selectedNodes;
    }

    public void setSelectedNodes(List<CloudNode> selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

    public synchronized void addSelectedNode(CloudNode node) {
        if (selectedNodes == null)
            selectedNodes = new ArrayList<CloudNode>();
        selectedNodes.add(node);
    }

    public boolean hasInstances() {
        return serviceInstances != null && serviceInstances.size() > 0;
    }

    @Override
    public List<String> getUris() {

        if (serviceInstances == null) {
            return new ArrayList<String>();
        }

        List<String> uris = new ArrayList<String>();
        synchronized (serviceInstances) {
            for (ServiceInstance service : serviceInstances) {
                uris.add(service.getNativeUri());
            }
        }
        return uris;
    }

    public ServiceInstance getInstanceById(String instanceId) {
        if (serviceInstances != null) {
            for (ServiceInstance instance : serviceInstances) {
                if (instance.getInstanceId().equals(instanceId))
                    return instance;
            }
        }
        throw new IllegalArgumentException("getSpec().getUUID() " + " / " + instanceId);
    }

    public RecipeBundle getRecipeBundle() {
        return recipeBundle;
    }

    public void setRecipeBundle(RecipeBundle recipeBundle) {
        this.recipeBundle = recipeBundle;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((serviceInstances == null) ? 0 : serviceInstances.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        DeployableService other = (DeployableService) obj;
        if (serviceInstances == null) {
            if (other.serviceInstances != null)
                return false;
        } else if (!serviceInstances.equals(other.serviceInstances))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder uris = new StringBuilder();
        for (String uri : getUris()) {
            uris.append(uri + "; ");
        }
        return "DeployableService [UUID=" + super.uuid + ", specName=" + super.spec.getName() + ", serviceInstances="
                + uris + "]";
    }

}
