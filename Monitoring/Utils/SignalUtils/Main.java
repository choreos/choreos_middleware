

public class Main {
	
	public static void main(String... args) {
		SignalSender s = new SignalSender();
		String ss = "gmond";
		System.out.println(s.sendSignalByProcessName(ss, 1));
	}
}
