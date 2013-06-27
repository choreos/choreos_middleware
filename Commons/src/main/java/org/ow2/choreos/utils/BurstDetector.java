/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.utils;

/**
 * Detects a burst of invocations to the invoke method.
 * 
 * A sequence of invocations is a burst if N invocation are performed within a
 * time DELTA. After a burst, the counter starts again. If the previous
 * invocation was a long time ago (more than DELTA) the counter is reseted too.
 * 
 * @author leonardo
 * 
 */
public class BurstDetector {

    private final int N;
    private final long DELTA;

    private int counter = 1;
    private long lastInvocationTime = 0;

    /**
     * 
     * @param n
     * @param delta
     *            in milliseconds
     */
    public BurstDetector(int n, long delta) {
        this.N = n;
        this.DELTA = delta;
    }

    /**
     * 
     * @return true if burst is detected; false otherwise
     */
    public boolean invoke() {

        long now = System.currentTimeMillis();
        long last = this.lastInvocationTime;
        this.lastInvocationTime = now;

        if (now - last < DELTA) {
            counter++;
            if (counter >= N) {
                counter = 0;
                return true;
            } else {
                return false;
            }
        } else {
            this.counter = 1;
            return false;
        }
    }
}
