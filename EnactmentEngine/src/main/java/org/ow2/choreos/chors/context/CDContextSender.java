package org.ow2.choreos.chors.context;

import java.util.List;

import org.ow2.choreos.chors.bus.EasyESBException;
import org.ow2.choreos.chors.bus.EasyESBNode;
import org.ow2.choreos.chors.bus.EasyESBNodeImpl;
import org.ow2.choreos.utils.URLUtils;

public class CDContextSender implements ContextSender {

    @Override
    public void sendContext(String serviceEndpoint, String partnerRole, String partnerName,
            List<String> partnerEndpoints) throws ContextNotSentException {
        String adminEndpoint = getAdminEndpoint(serviceEndpoint);
        EasyESBNode esb = new EasyESBNodeImpl(adminEndpoint);
        for (String partnerEndpoint : partnerEndpoints) {
            String neighbourAdminEndpoint = getAdminEndpoint(partnerEndpoint);
            if (!neighbourAdminEndpoint.equals(adminEndpoint)) {
                EasyESBNode neighbour = new EasyESBNodeImpl(neighbourAdminEndpoint);
                try {
                    esb.addNeighbour(neighbour);
                } catch (EasyESBException e) {
                    throw new ContextNotSentException(serviceEndpoint, partnerRole, partnerName, partnerEndpoints);
                }
            }
        }
    }

    private String getAdminEndpoint(String cdEndpoint) {
        String ip = URLUtils.extractIpFromURL(cdEndpoint);
        return "http://" + ip + ":8180/services/adminExternalEndpoint";
    }

}
