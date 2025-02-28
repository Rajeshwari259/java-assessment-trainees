package main.java.com.atyeti.csvreport.model;

public class SensorData {
    private String date;
    private String sensorType;
    private double value;
    private String unit;
    private String locationId;
    private String month;

    public SensorData(String date, String sensorType, double value, String unit, String locationId, String month) {
        this.date = date;
        this.sensorType = sensorType;
        this.value = value;
        this.unit = unit;
        this.locationId = locationId;
        this.month = month;
    }

    public String getDate() { return date; }
    public String getSensorType() { return sensorType; }
    public double getValue() { return value; }
    public String getUnit() { return unit; }
    public String getLocationId() { return locationId; }
    public String getMonth() { return month; }
}

