package main.java.com.atyeti.csvreport.model;


public class ThresholdData {
    private String sensorType;
    private double minThreshold;
    private double maxThreshold;

    public ThresholdData(String sensorType, double minThreshold, double maxThreshold) {
        this.sensorType = sensorType;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }

    public String getSensorType() {
        return sensorType;
    }

    public double getMinThreshold() {
        return minThreshold;
    }

    public double getMaxThreshold() {
        return maxThreshold;
    }
}
