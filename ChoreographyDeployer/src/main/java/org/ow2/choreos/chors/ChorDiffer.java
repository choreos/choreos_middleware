package org.ow2.choreos.chors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class ChorDiffer {

    private Choreography chor;
    private List<DeployableServiceSpec> toCreate;
    private Map<DeployableService, DeployableServiceSpec> toUpdate;
    private List<DeployableService> notModify;

    public ChorDiffer(Choreography chor) {
        this.chor = chor;
    }

    public List<DeployableServiceSpec> getNewServiceSpecs() {
        if (toCreate == null)
            diff();
        return toCreate;
    }

    public Map<DeployableService, DeployableServiceSpec> getServicesToUpdate() {
        if (toUpdate == null)
            diff();
        return toUpdate;
    }

    public List<DeployableService> getNotModifiedServices() {
        if (notModify == null)
            diff();
        return notModify;
    }

    private void diff() {
        
        toCreate = new ArrayList<DeployableServiceSpec>();
        toUpdate = new HashMap<DeployableService, DeployableServiceSpec>();
        notModify = new ArrayList<DeployableService>();
        
        List<DeployableServiceSpec> specsList = chor.getRequestedChoreographySpec().getDeployableServiceSpecs();
        Map<String, DeployableServiceSpec> requestedSpecMap = makeMapFromServiceList(specsList);
        Map<String, DeployableService> currentServices = getServicesForChor();

        for (Map.Entry<String, DeployableService> currentServiceEntry : currentServices.entrySet()) {
            DeployableServiceSpec requestedSpec = requestedSpecMap.get(currentServiceEntry.getKey());
            DeployableService currentService = currentServiceEntry.getValue();
            if (requestedSpec != null) {
                if (!requestedSpec.equals(currentService.getSpec())) {
                    toUpdate.put(currentService, requestedSpec);
                } else {
                    notModify.add(currentService);
                }
            }
        }

        for (Map.Entry<String, DeployableServiceSpec> specEntry : requestedSpecMap.entrySet()) {
            if (!currentServices.containsKey(specEntry.getKey())) {
                toCreate.add(specEntry.getValue());
            }
        }
    }

    private Map<String, DeployableServiceSpec> makeMapFromServiceList(List<DeployableServiceSpec> list) {
        Map<String, DeployableServiceSpec> result = new HashMap<String, DeployableServiceSpec>();
        for (DeployableServiceSpec spec : list)
            result.put(spec.getName(), spec);
        return result;
    }

    private Map<String, DeployableService> getServicesForChor() {
        Map<String, DeployableService> currentServices = new HashMap<String, DeployableService>();
        if (chor.getDeployableServices() != null) {
            for (DeployableService s : chor.getDeployableServices()) {
                currentServices.put(s.getSpec().getName(), s);
            }
        }
        return currentServices;
    }

}
