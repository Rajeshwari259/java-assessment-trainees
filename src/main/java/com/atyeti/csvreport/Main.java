package main.java.com.atyeti.csvreport;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.com.atyeti.csvreport.ingestion.DataIngestion;
import main.java.com.atyeti.csvreport.model.SensorData;
import main.java.com.atyeti.csvreport.model.ThresholdData;
import main.java.com.atyeti.csvreport.outlier.OutlierDetection;
import main.java.com.atyeti.csvreport.processing.ProcessingModule;
import main.java.com.atyeti.csvreport.reporting.ReportingModule;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        DataIngestion dataIngestion = new DataIngestion();
        OutlierDetection outlierDetection = new OutlierDetection();
        ProcessingModule processingModule = new ProcessingModule();
        ReportingModule reportingModule = new ReportingModule();

        try {
            List<SensorData> sensorDataList = dataIngestion.readSensorData("sensor_data.csv");
            logger.info("Sensor data successfully read from 'sensor_data.csv'.");

            Map<String, ThresholdData> thresholds = dataIngestion.readThresholds("thresholds.csv");
            logger.info("Threshold data successfully read from 'thresholds.csv'.");

            Map<String, List<SensorData>> outliers = outlierDetection.detectOutliers(sensorDataList, thresholds);
            logger.info("Outliers detected successfully.");

            Map<String, Map<String, Double[]>> monthlyStats = processingModule.calculateMonthlyStats(sensorDataList);
            logger.info("Monthly statistics calculated successfully.");

            reportingModule.generateMonthlyStatsReport(monthlyStats, "monthly_stats.csv");
            logger.info("Monthly stats report successfully generated and saved as 'monthly_stats.csv'.");

            reportingModule.generateOutliersReport(outliers, thresholds, "outliers.csv");
            logger.info("Outliers report successfully generated and saved as 'outliers.csv'.");

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error in processing the sensor data files: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error: " + e.getMessage(), e);
        }
    }
}