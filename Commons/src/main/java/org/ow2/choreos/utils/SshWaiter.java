/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * Returns a SSH connection to a give node.
 * 
 * If connection is not successfully established within the given timeout, an
 * exception is thrown.
 * 
 * @author leonardo
 * 
 */
public class SshWaiter {

    private Logger logger = Logger.getLogger(SshWaiter.class);

    public SshUtil waitSsh(String ip, String user, String keyPath, int timeoutSeconds) throws SshNotConnected {

	SshWaiterCallable callable = new SshWaiterCallable(ip, user, keyPath);
	ExecutorService executor = Executors.newSingleThreadExecutor();
	Future<SshUtil> future = executor.submit(callable);

	Concurrency.waitExecutor(executor, timeoutSeconds, TimeUnit.SECONDS, logger);

	try {
	    SshUtil ssh = Concurrency.checkFuture(future);
	    if (ssh != null) {
		return ssh;
	    } else {
		throw new SshNotConnected("Could not SSH into " + ip);
	    }
	} catch (ExecutionException e) {
	    throw new SshNotConnected("Could not SSH into " + ip);
	} catch (IllegalStateException e) {
	    throw new SshNotConnected("Could not SSH into " + ip);
	}
    }

    private class SshWaiterCallable implements Callable<SshUtil> {

	private String ip, user, keyPath;

	public SshWaiterCallable(String ip, String user, String keyPath) {
	    this.ip = ip;
	    this.user = user;
	    this.keyPath = keyPath;
	}

	@Override
	public SshUtil call() throws Exception {

	    logger.debug("Waiting for SSH to " + ip);
	    SshUtil ssh = new SshUtil(ip, user, keyPath);
	    while (!ssh.isAccessible()) {
		logger.debug("Trying SSH into " + ip + " again in 5 seconds");
		try {
		    Thread.sleep(5000);
		} catch (InterruptedException e) {
		    String msg = "VM " + ip + " not accessible!";
		    logger.warn(msg);
		    throw new IllegalStateException(msg);
		}
	    }
	    ssh.disconnect();
	    logger.debug("Connected to " + ip);
	    return ssh;
	}
    }

}
