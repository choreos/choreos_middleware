package org.ow2.choreos.experiment.vms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExecutionRecorder {

	public void record(List<Long> times, int n, int i) throws IOException {
		
		File recordFile = new File("vm_results/" + n + "vms_" + i + "exec.txt");
		FileWriter writer = new FileWriter(recordFile);
		
		for (long t: times) {
			writer.write(Long.toString(t) + "\n");
		}
		
		writer.close();
	}
	
	public static void main(String[] args) throws IOException {
		
		// test
		List<Long> times = new ArrayList<Long>();
		times.add(new Long(5));
		times.add(new Long(7));
		times.add(new Long(5));
		times.add(new Long(3));
		ExecutionRecorder r = new ExecutionRecorder();
		r.record(times, 4, 1);
	}
}
