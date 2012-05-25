package eu.choreos.signal.sender;

class SignalSender {
	
	native int sendSignalByPID(int pid, int sig);
	native int sendSignalByProcessName(String name, int sig);

	static {
		NarSystem.loadLibrary();
	}

	public static void main(String[] args) {

		SignalSender sigsender = new SignalSender();

		/*
		 Example of uses

		 sendsignal <signal> -n name -s 1
		 sendsignal <signal> -p pid -s 1 
		*/

		if(args.length != 5) {
			System.out.println("Usage: TODO");
			return;

		}

		if( args[1] == "-n" ) {
			sigsender.sendSignalByProcessName(args[2], Integer.parseInt(args[4]));
		}
		else if ( args[1] == "-p" ) {
			sigsender.sendSignalByPID(Integer.parseInt(args[2]), Integer.parseInt(args[4]));
		}
	}
}
