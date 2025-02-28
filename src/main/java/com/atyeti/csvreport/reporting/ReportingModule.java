package main.java.com.atyeti.csvreport.reporting;

import main.java.com.atyeti.csvreport.model.SensorData;
import main.java.com.atyeti.csvreport.model.ThresholdData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ReportingModule {
    private static final Logger logger = Logger.getLogger(ReportingModule.class.getName());

    public void generateMonthlyStatsReport(Map<String, Map<String, Double[]>> monthlyStats, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("sensor_type,month,avg_value,max_value,min_value\n");

            for (Map.Entry<String, Map<String, Double[]>> entry : monthlyStats.entrySet()) {
                String sensorType = entry.getKey();
                for (Map.Entry<String, Double[]> monthEntry : entry.getValue().entrySet()) {
                    String month = monthEntry.getKey();
                    Double[] stats = monthEntry.getValue();

                    double avg = stats[0] / stats[1]; // Calculate the average value

                    writer.write(String.format("%s,%s,%.2f,%.2f,%.2f\n", sensorType, month, avg, stats[2], stats[3]));
                }
            }
        } catch (IOException e) {
            logger.severe("ERR003: Processing error while generating monthly stats report.");
        }
    }

    public void generateOutliersReport(Map<String, List<SensorData>> outliers, Map<String, ThresholdData> thresholds, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("date,sensor_type,value,unit,location_id,month,threshold_exceeded\n");

            for (Map.Entry<String, List<SensorData>> entry : outliers.entrySet()) {
                for (SensorData data : entry.getValue()) {
                    ThresholdData threshold = thresholds.get(data.getSensorType());

                    if (threshold != null) {
                        String thresholdExceeded = data.getValue() < threshold.getMinThreshold() ? "Min" : "Max";
                        writer.write(String.format("%s,%s,%.2f,%s,%s,%s,%s\n",
                                data.getDate(),
                                data.getSensorType(),
                                data.getValue(),
                                data.getUnit(),
                                data.getLocationId(),
                                data.getMonth(),
                                thresholdExceeded));
                    }
                }
            }
        } catch (IOException e) {
            logger.severe("ERR003: Processing error while generating outliers report.");
        }
    }
}