/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.datamodel;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.services.datamodel.Service;

public class LegacyService extends Service {

    private List<LegacyServiceInstance> legacyServiceInstances;

    public LegacyService() {

    }
    
    public LegacyService(LegacyServiceSpec serviceSpec) {
        super(serviceSpec);
        buildInstances(serviceSpec);
    }
    
    private void buildInstances(LegacyServiceSpec serviceSpec) {
        legacyServiceInstances = new ArrayList<LegacyServiceInstance>();
        for (String uri: serviceSpec.getNativeURIs()) {
            LegacyServiceInstance legacyInstance = new LegacyServiceInstance();
            legacyInstance.setUri(uri);
            legacyServiceInstances.add(legacyInstance);
        }
    }
    
    public List<LegacyServiceInstance> getLegacyServiceInstances() {
        return legacyServiceInstances;
    }

    public void setLegacyServiceInstances(List<LegacyServiceInstance> legacyServiceInstances) {
        this.legacyServiceInstances = legacyServiceInstances;
    }

    @Override
    public List<String> getUris() {
        List<String> uris = new ArrayList<String>();
        if (legacyServiceInstances != null) {
            for (LegacyServiceInstance legacyInstance: legacyServiceInstances)
                uris.add(legacyInstance.getUri());
        }
        return uris;
    }

}
