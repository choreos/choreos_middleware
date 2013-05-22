package org.ow2.choreos.experiment.enact;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Report {

	int run;
	private boolean calculated = false;
	
	int vmsQuantity;
	List<Double> vmsCreationTimes = new ArrayList<Double>();
	double vmsCreatoinMeanTime;
	double vmsCreationStdDev;
	double vmsCreationTotalTime;
	
	int chorsQuantity;
	List<Double> chorsEnactmentTimes = new ArrayList<Double>();
	double chorsEnactmentMeanTime;
	double chorsEnactmentStdDev;
	double chorsEnactmentTotalTime;
	int chorsWorking;
	
	List<Double> checkTimes = new ArrayList<Double>();
	double checkMeanTime;
	double checkStdDev;
	double checkTotalTime;
	
	double totalTimeWithoutCheck;
	double totalTime;
	
	public Report(int run) {
		this.run = run;
	}
	
	public void setVmsQuantity(int vmsQuantity) {
		this.vmsQuantity = vmsQuantity;
	}
	
	public void setChorsQuantity(int chorsQuantity) {
		this.chorsQuantity = chorsQuantity;
	}

	public void setChorsWorking(int chorsWorking) {
		this.chorsWorking = chorsWorking;
	}

	public void setTotalTimeWithoutCheck(long totalTimeWithoutCheck) {
		this.totalTimeWithoutCheck = totalTimeWithoutCheck / 1000.0;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime / 1000.0;
	}
	
	public void setVmsCreationTotalTime(long vmsCreationTotalTime) {
		this.vmsCreationTotalTime = vmsCreationTotalTime / 1000.0;
	}

	public void setChorsEnactmentTotalTime(long chorsEnactmentTotalTime) {
		this.chorsEnactmentTotalTime = chorsEnactmentTotalTime / 1000.0;
	}

	public void setCheckTotalTime(long checkTotalTime) {
		this.checkTotalTime = checkTotalTime / 1000.0;
	}

	public synchronized void addVmCreationTime(long vmCreatoinTime) {
		this.vmsCreationTimes.add(vmCreatoinTime / 1000.0);
	}
	
	public synchronized void addChorEnactmentTime(long chorEnactmentTime) {
		this.chorsEnactmentTimes.add(chorEnactmentTime / 1000.0);
	}
	
	public synchronized void addCheckTime(long checkTime) {
		this.checkTimes.add(checkTime / 1000.0);
	}
	
	private double mean(List<Double> values) {
		
		double sum = 0;
		for (double v: values) {
			sum += v;
		}
		return 1.0 * sum / values.size();
	}
	
	private double stdDev(List<Double> values, double mean) {
		
		double sum = 0;
		for (double v: values) {
			sum += (v - mean) * (v - mean);
		}
		return 1.0 * sum / Math.sqrt(values.size());
	}
	
	private void calculate() {

		if (!this.calculated) {
			
			this.calculated = true;
			
			this.vmsCreatoinMeanTime = this.mean(this.vmsCreationTimes);
			this.vmsCreationStdDev = this.stdDev(this.vmsCreationTimes, this.vmsCreatoinMeanTime);
			this.chorsEnactmentMeanTime = this.mean(this.chorsEnactmentTimes);
			this.chorsEnactmentStdDev = this.stdDev(this.chorsEnactmentTimes, this.chorsEnactmentMeanTime);
			this.checkMeanTime = this.mean(this.checkTimes);
			this.checkStdDev = this.stdDev(this.checkTimes, this.checkMeanTime);
			
			this.totalTimeWithoutCheck += this.vmsCreationTotalTime;
			this.totalTime += this.vmsCreationTotalTime;
		}
	}
	
	public void toFile() throws IOException {
		
		String report = this.toString();
		File file = new File("results/" + chorsQuantity + "chors_" + run + "run.txt");
		Writer w = new FileWriter(file);
		w.append(report + "\n");
		w.close();
	}

	@Override
	public String toString() {
		this.calculate();
		return "########Report:########## "
				+ "\n // tuples are (mean, std dev)"
				+ "\n // times in seconds"
				+ "\n " + run + "/10 with " + chorsQuantity + " chors; @LCPD " + (new Date()).toString()
				+ "\n How many VMs were ordered = " + vmsQuantity
				+ "\n How many VMs were created = " + vmsCreationTimes.size()
				+ "\n Time to create a VM = (" + vmsCreatoinMeanTime + ", " + vmsCreationStdDev + ")"
				+ "\n Total time to create VMs = " + vmsCreationTotalTime
				+ "\n How many choreographies to enact = " + chorsQuantity
				+ "\n How many choreographies enacted = " + chorsEnactmentTimes.size()
				+ "\n Time to enact a choreography = (" + chorsEnactmentMeanTime + ", " + chorsEnactmentStdDev + ")"
				+ "\n Total time to enact choreographies = " + chorsEnactmentTotalTime
				+ "\n Time to check a choreography = (" + checkMeanTime + ", " + checkStdDev + ")"
				+ "\n Total time to check choreographies = " + checkTotalTime
				+ "\n How many choreographies working = " + chorsWorking
				+ "\n Total time without checking choreographies = " + totalTimeWithoutCheck 
				+ "\n Total time = " + totalTime + "\n";
	}
}
