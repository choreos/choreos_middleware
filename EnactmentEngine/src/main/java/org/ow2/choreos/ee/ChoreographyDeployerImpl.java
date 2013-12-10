/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;

public class ChoreographyDeployerImpl implements ChoreographyDeployer {

    private ChorRegistry reg = ChorRegistry.getInstance();

    private Logger logger = Logger.getLogger(ChoreographyDeployerImpl.class);

    @Override
    public String createChoreography(ChoreographySpec chor) {
	String chorId = reg.create(chor);
	logger.info("Choreography " + chorId + " created.");
	return chorId;
    }

    @Override
    public Choreography getChoreography(String chorId) {
	Choreography chor = reg.getChoreography(chorId);
	return chor;
    }

    @Override
    public Choreography enactChoreography(String chorId) throws EnactmentException, ChoreographyNotFoundException {
	if (!reg.contains(chorId))
	    throw new ChoreographyNotFoundException(chorId);
	Choreography chor = reg.getChoreography(chorId);
	ChoreographyEnacter enacter = new ChoreographyEnacter(chor);
	return enacter.enact();
    }

    @Override
    public void updateChoreography(String chorId, ChoreographySpec spec) throws ChoreographyNotFoundException {
	logger.info("Requested to update choreography " + chorId);
	Choreography chor = reg.getChoreography(chorId);
	if (chor == null) {
	    throw new ChoreographyNotFoundException(chorId);
	}
	if (spec.equals(chor.getChoreographySpec())) {
	    logger.info("Requested to update choreography with the same spec that already have");
	    return;
	}
	ChoreographyContext ctx = reg.getContext(chorId);
	ctx.setRequestedChoreographySpec(spec);
    }

}
