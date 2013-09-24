/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services.datamodel;

import org.ow2.choreos.nodes.datamodel.CloudNode;

public class ServiceInstance {

    private String instanceId; // The effective service instance ID
    private String nativeUri; // URI for access instance directly
    private CloudNode node; // The hosting node

    private Proxification proxification;

    private DeployableServiceSpec serviceSpec;

    // used by JAXB
    public ServiceInstance() {

    }

    public ServiceInstance(CloudNode node) {
        this.setNode(node);
    }

    public DeployableServiceSpec getServiceSpec() {
        return serviceSpec;
    }

    public void setServiceSpec(DeployableServiceSpec serviceSpec) {
        this.serviceSpec = serviceSpec;
    }

    public CloudNode getNode() {
        return node;
    }

    public void setNode(CloudNode node) {
        this.node = node;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
    
    public Proxification getProxification() {
        return proxification;
    }

    public void setProxification(Proxification proxification) {
        this.proxification = proxification;
    }

    public void setNativeUri(String uri) {
        this.nativeUri = uri;
    }
    
    public String getNativeUri() {
        if (nativeUriIsDefined()) {
            return nativeUri;
        } else {
            return getDefaultnativeUri();
        }
    }

    private boolean nativeUriIsDefined() {
        return nativeUri != null && !nativeUri.isEmpty();
    }

    private String getDefaultnativeUri() {
        NativeUriRetriever retriever = new NativeUriRetriever(this);
        return retriever.getDefaultnativeUri();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((instanceId == null) ? 0 : instanceId.hashCode());
        result = prime * result + ((nativeUri == null) ? 0 : nativeUri.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ServiceInstance other = (ServiceInstance) obj;
        if (instanceId == null) {
            if (other.instanceId != null)
                return false;
        } else if (!instanceId.equals(other.instanceId))
            return false;
        if (nativeUri == null) {
            if (other.nativeUri != null)
                return false;
        } else if (!nativeUri.equals(other.nativeUri))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ServiceInstance [instanceId=" + instanceId + ", nativeUri=" + nativeUri + "]";
    }

}
