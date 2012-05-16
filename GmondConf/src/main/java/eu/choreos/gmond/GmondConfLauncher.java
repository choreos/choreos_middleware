package eu.choreos.gmond;


public class GmondConfLauncher {
	private static GmondConf gmondConf = null;
	
	public static void main(String... args) throws Exception {
		String fileName = "/etc/ganglia/gmond.conf";

		validateArgs(args);

		fileName = getConfigFile(args);

		if (gmondConf == null)
		gmondConf = new GmondConf();

		gmondConf.load(fileName);

		for (int i = 0; i < args.length; i++) {

			if (args[i].equals("--add")) {
				gmondConf.addUdpSendChannel(args[i + 1], args[i + 2]);
				i += 2;
			}

			if (args[i].equals("--update-port")) {
				gmondConf.updateUdpSendChannelPort(args[i + 1], args[i + 2]);
				i += 2;
			}

			if (args[i].equals("--update-channel")) {
				gmondConf.updateUdpSendChannel(args[i + 1], args[i + 2],
						args[i + 3]);
				i += 3;
			}

			if (args[i].equals("--update-host")) {
				gmondConf.updateUdpSendChannelHost(args[i + 1], args[i + 2]);
				i += 2;
			}

			if (args[i].equals("--remove")) {
				gmondConf.removeUdpSendChannel(args[i + 1]);
				i += 2;
			}

		}

		gmondConf.save();
		
		gmondConf.reloadConfigurations();
		
	}

	private static String getConfigFile(String[] arguments) {
		String fileName = "/etc/ganglia/gmond.conf";

		for (int i = 0; i < arguments.length; i++) {

			if (arguments[i].equals("--config-file")) {
				fileName = arguments[i + 1];
			}
		}
		return fileName;
	}

	private static void validateArgs(String[] args) throws Exception {
		String string;

		for (int index = 0; index < args.length; index++) {
			string = args[index];

			if (string.equals("--add"))
				index = index + 2;
			else if (string.equals("--update-port"))
				index = index + 2;
			else if (string.equals("--update-channel"))
				index = index + 3;
			else if (string.equals("--update-host"))
				index = index + 2;
			else if (string.equals("--remove"))
				index = index + 1;
			else if (string.equals("--config-file"))
				index = index + 1;
			else {
				printUsage();
				System.exit(1);
			}
		}
	}

	private static void printUsage() throws Exception {
		System.out.println("" + "USAGE: java -jar gmondconf "
				+ "[--add host port] " + "[--update-port host port] "
				+ "[--update-host currentHost newHost] "
				+ "[--update-channel currentHost newHost newPort]"
				+ "[--remove host]" + "[--config-file configFile]");
		throw (new Exception());
	}

	public static void setGmondConf(GmondConf gmondConf) {
		GmondConfLauncher.gmondConf = gmondConf;
	}
}