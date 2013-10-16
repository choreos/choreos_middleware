package org.ow2.choreos.deployment.nodes;

import org.ow2.choreos.deployment.DeploymentManagerConfiguration;

public class IdlePoolFactory {

    public static IdlePool idlePoolForTesting;
    public static boolean testing;

    public IdlePool getNewIdlePool() {
        if (testing) {
            return idlePoolForTesting;
        } else {
            return loadPool();
        }
    }

    private IdlePool loadPool() {
        int poolSize = 0;
        try {
            poolSize = Integer.parseInt(DeploymentManagerConfiguration.get("IDLE_POOL_SIZE"));
        } catch (NumberFormatException e) {
            ; // no problem, poolSize is zero
        }
        return IdlePool.getInstance(poolSize);
    }
}
