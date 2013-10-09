package org.ow2.choreos.tracker.experiment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Times must be set in nanoseconds
 * 
 * @author leonardo
 * 
 */
public class Report {

    private static final double CONVERSOR = 1000000000.0;

    private static int repetitions = 10;
    
    int run;
    int vmLimit;

    boolean calculated;

    int chorsQty;
    int chorsSize;
    List<Double> chorsEnactmentTimes = new ArrayList<Double>();
    double chorsEnactmentMeanTime;
    double chorsEnactmentStdDev;
    double chorsEnactmentTotalTime;

    List<Double> checkTimes = new ArrayList<Double>();
    double checkMeanTime;
    double checkStdDev;
    double checkTotalTime;

    int chorsWorking;
    int servicesWorking;

    double totalTime;

    String header;

    public Report(int run, int chorsQty, int chorsSize, int vmLimit) {
        this.run = run;
        this.chorsQty = chorsQty;
        this.chorsSize = chorsSize;
        this.vmLimit = vmLimit;
        this.header = run + "/" + repetitions + " with " + chorsQty + " chors of size " + chorsSize + "; "
                + (new Date()).toString();
    }

    public static void setRepetitions(int repetitions) {
        Report.repetitions = repetitions;
    }

    public void setChorsQuantity(int chorsQty) {
        this.chorsQty = chorsQty;
    }

    public void setChorsWorking(int chorsWorking) {
        this.chorsWorking = chorsWorking;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime / CONVERSOR;
    }

    public void setChorsEnactmentTotalTime(long chorsEnactmentTotalTime) {
        this.chorsEnactmentTotalTime = chorsEnactmentTotalTime / CONVERSOR;
    }

    public void setCheckTotalTime(long checkTotalTime) {
        this.checkTotalTime = checkTotalTime / CONVERSOR;
    }

    public void setServicesWorking(int servicesWorking) {
        this.servicesWorking = servicesWorking;
    }

    public synchronized void addChorEnactmentTime(long chorEnactmentTime) {
        this.chorsEnactmentTimes.add(chorEnactmentTime / CONVERSOR);
    }

    public synchronized void addCheckTime(long checkTime) {
        this.checkTimes.add(checkTime / CONVERSOR);
    }

    private double mean(List<Double> values) {

        double sum = 0;
        for (double v : values) {
            sum += v;
        }
        return 1.0 * sum / values.size();
    }

    private double stdDev(List<Double> values, double mean) {

        double sum = 0;
        for (double v : values) {
            sum += (v - mean) * (v - mean);
        }
        int n = values.size();
        return Math.sqrt(1.0 * sum / (n - 1));
    }

    private void calculate() {

        if (!this.calculated) {

            this.calculated = true;

            this.chorsEnactmentMeanTime = this.mean(this.chorsEnactmentTimes);
            this.chorsEnactmentStdDev = this.stdDev(this.chorsEnactmentTimes, this.chorsEnactmentMeanTime);
            this.checkMeanTime = this.mean(this.checkTimes);
            this.checkStdDev = this.stdDev(this.checkTimes, this.checkMeanTime);
        }
    }

    public void toFile() throws IOException {

        String report = this.toString();
        File file = new File("results/chor" + chorsQty + "x" + chorsSize + "_" + vmLimit + "vms_" + run + "run.txt");
        Writer w = new FileWriter(file);
        w.append(report + "\n");
        w.close();
    }

    @Override
    public String toString() {
        this.calculate();
        return "########Report:########## " + "\n // tuples are (mean, std dev)" + "\n // times in seconds" + "\n "
                + header + "\n VM_LIMIT = " + vmLimit + "\n How many choreographies to enact = " + chorsQty
                + "\n How many choreographies enacted = " + chorsEnactmentTimes.size()
                + "\n Time to enact choreographies = " + chorsEnactmentTimes
                + "\n Mean time to enact a choreography = (" + chorsEnactmentMeanTime + ", " + chorsEnactmentStdDev
                + ")" + "\n Total time to enact choreographies = " + chorsEnactmentTotalTime
                + "\n Time to check choreographies = " + checkTimes + "\n Mean time to check a choreography = ("
                + checkMeanTime + ", " + checkStdDev + ")" + "\n Total time to check choreographies = "
                + checkTotalTime + "\n How many choreographies working = " + chorsWorking + " / " + (chorsQty)
                + "\n How many services working = " + servicesWorking + " / " + (chorsQty * chorsSize)
                + "\n Total time = " + totalTime + "\n";
    }

}
