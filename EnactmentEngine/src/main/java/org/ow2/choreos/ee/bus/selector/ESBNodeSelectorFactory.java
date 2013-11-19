package org.ow2.choreos.ee.bus.selector;

import org.apache.log4j.Logger;
import org.ow2.choreos.ee.ChoreographyDeployerConfiguration;
import org.ow2.choreos.utils.Configuration;
import org.ow2.choreos.utils.SingletonsFactory;

public class ESBNodeSelectorFactory extends SingletonsFactory<ESBNodeSelector> {

    private static final String BUS_POLICY_PROPERTY = "BUS_POLICY";
    private static final String CLASS_MAP_FILE_PATH = "bus_policies.properties";

    private static ESBNodeSelectorFactory INSTANCE;

    private static Logger logger = Logger.getLogger(ESBNodeSelectorFactory.class);

    public static ESBNodeSelectorFactory getFactoryInstance() {
        if (INSTANCE == null) {
            synchronized (ESBNodeSelectorFactory.class) {
                if (INSTANCE == null)
                    createNewInstance();
            }
        }
        return INSTANCE;
    }

    private static void createNewInstance() {
        Configuration conf = new Configuration(CLASS_MAP_FILE_PATH);
        INSTANCE = new ESBNodeSelectorFactory(conf);
    }

    private ESBNodeSelectorFactory(Configuration classMap) {
        super(classMap);
    }

    public ESBNodeSelector getNodeSelectorInstance() {
        String selectorType = ChoreographyDeployerConfiguration.get(BUS_POLICY_PROPERTY);
        if (selectorType == null) {
            logger.error(BUS_POLICY_PROPERTY + " property not set on properties file!");
            throw new IllegalArgumentException();
        }
        return getInstance(selectorType);
    }

}
