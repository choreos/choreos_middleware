package org.ow2.choreos.experiment.vms;

import org.apache.log4j.Logger;
import org.ow2.choreos.utils.SshUtil;

public class VMChecker {
	
	private Logger logger = Logger.getLogger(VMChecker.class);

	private static final String USER = "ubuntu";
	private static final String KEY = "/home/leonardo/.ssh/leoflaws.pem";
	
	public void check(String ip) {
		
        final int DELAY = 5000;
        
        SshUtil ssh = new SshUtil(ip, USER, KEY);
        while (!ssh.isAccessible()) {
        	logger.debug("Trying SSH into " + ip + " again in " + DELAY/1000 + " seconds");
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
            	logger.error("Exception at sleeping! Thread was killed! Node " + ip);
            	return;
            }
        }
        ssh.disconnect();
	}
}
