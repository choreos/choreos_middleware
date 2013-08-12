package eu.choreos.monitoring.platform.daemon;

import java.io.IOException;

public class ThresholdEvalDaemonLauncher {

    public static void main(String[] args) throws InterruptedException, IOException {

	new ThresholdEvalDaemonService().start();

    }
}
