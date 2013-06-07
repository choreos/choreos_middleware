package org.ow2.choreos.chors.bus.selector;

import java.util.List;

import org.ow2.choreos.chors.bus.ESBRegister;
import org.ow2.choreos.chors.bus.EasyESBNode;
import org.ow2.choreos.selectors.ObjectRetriever;

public class ESBNodeRetriever implements ObjectRetriever<EasyESBNode> {

    @Override
    public List<EasyESBNode> retrieveObjects() {
	return ESBRegister.getEsbNodes();
    }

}
