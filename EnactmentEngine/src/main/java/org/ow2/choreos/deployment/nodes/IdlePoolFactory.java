package org.ow2.choreos.deployment.nodes;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.CloudConfiguration;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;

public class IdlePoolFactory {

    private static final int DEFAULT_POOL_SIZE = 0;
    private static final int DEFAULT_POOL_THRESHOLD = -1;

    public static IdlePool idlePoolForTesting;
    public static boolean testing;

    private static Map<String, IdlePool> INSTANCES = new HashMap<String, IdlePool>();
    private static Logger logger = Logger.getLogger(IdlePoolFactory.class);

    /**
     * Thread safe
     * 
     * @param poolSize
     * @param nodeCreator
     * @return
     */
    public static IdlePool getInstance(CloudConfiguration cloudConfiguration, int poolSize, int threshold) {
	String cloudAccount = cloudConfiguration.getOwner();
	synchronized (IdlePoolFactory.class) {
	    if (!INSTANCES.containsKey(cloudAccount)) {
		INSTANCES.put(cloudAccount, new IdlePool(cloudConfiguration, poolSize, threshold));
	    }
	}
	return INSTANCES.get(cloudAccount);
    }

    /**
     * Not thread safe. To test purposes
     * 
     * @param poolSize
     * @param nodeCreator
     * @return
     */
    public static IdlePool getCleanInstance(CloudConfiguration cloudConfiguration, int poolSize, int threshold) {
	return new IdlePool(cloudConfiguration, poolSize, threshold);
    }

    public IdlePool getIdlePool(CloudConfiguration cloudConfiguration) {
	if (testing) {
	    return idlePoolForTesting;
	} else {
	    return loadPool(cloudConfiguration);
	}
    }

    private IdlePool loadPool(CloudConfiguration cloudConfiguration) {
	int poolSize = getValue("IDLE_POOL_INITIAL_SIZE", DEFAULT_POOL_SIZE);
	int threshold = getValue("IDLE_POOL_THRESHOLD", DEFAULT_POOL_THRESHOLD);
	return getInstance(cloudConfiguration, poolSize, threshold);
    }

    private int getValue(String property, int defaultValue) {
	int value = defaultValue;
	try {
	    value = Integer.parseInt(DeploymentManagerConfiguration.get(property));
	} catch (NumberFormatException e) {
	    logger.warn(property + " not integer. Going to use default " + defaultValue);
	} catch (IllegalArgumentException e) {
	    logger.warn(property + " not found. Going to use default " + defaultValue);
	}
	return value;
    }

}
