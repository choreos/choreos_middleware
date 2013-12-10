package org.ow2.choreos.ee.bus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ESBRegister {

    // key is the node admin endpoint
    private static Map<String, EasyESBNode> esbNodes = new HashMap<String, EasyESBNode>();

    public static synchronized void addEsbNode(EasyESBNode esbNode) {
        esbNodes.put(esbNode.getAdminEndpoint(), esbNode);
    }

    public static synchronized List<EasyESBNode> getEsbNodes() {
        List<EasyESBNode> list = new ArrayList<EasyESBNode>(esbNodes.values());
        return Collections.unmodifiableList(list);
    }
    
    public static synchronized EasyESBNode getEsbNode(String adminEndpoint) {
        return esbNodes.get(adminEndpoint);
    }
    
    public static synchronized void clear() {
        esbNodes.clear();
    }

}
