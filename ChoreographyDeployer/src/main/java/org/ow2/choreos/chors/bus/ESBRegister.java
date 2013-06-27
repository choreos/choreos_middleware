package org.ow2.choreos.chors.bus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ESBRegister {

    private static List<EasyESBNode> esbNodes = new ArrayList<EasyESBNode>();

    public static synchronized void addEsbNode(EasyESBNode esbNode) {
        esbNodes.add(esbNode);
    }

    public static synchronized List<EasyESBNode> getEsbNodes() {
        return Collections.unmodifiableList(esbNodes);
    }

}
