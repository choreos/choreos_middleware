package org.ow2.choreos.deployment.nodes;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;


public class IdlePoolFactory {
    
    private static final int DEFAULT_POOL_SIZE = 0;
    private static final int DEFAULT_POOL_THRESHOLD = -1;

    public static IdlePool idlePoolForTesting;
    public static boolean testing;
    
    private static Logger logger = Logger.getLogger(IdlePoolFactory.class);

    public IdlePool getNewIdlePool() {
        if (testing) {
            return idlePoolForTesting;
        } else {
            return loadPool();
        }
    }

    private IdlePool loadPool() {
        int poolSize = getValue("IDLE_POOL_INITIAL_SIZE", DEFAULT_POOL_SIZE);
        int threshold = getValue("IDLE_POOL_THRESHOLD", DEFAULT_POOL_THRESHOLD);
        return IdlePool.getInstance(poolSize, threshold);
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
