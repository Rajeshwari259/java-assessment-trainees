package main.java.com.atyeti.csvreport.ingestion;

import main.java.com.atyeti.csvreport.errorhandling.ErrorHandling;
import main.java.com.atyeti.csvreport.model.SensorData;
import main.java.com.atyeti.csvreport.model.ThresholdData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataIngestion {

    public List<SensorData> readSensorData(String filePath) throws IOException {
        List<SensorData> sensorDataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + filePath))) {
            String line;
            br.readLine(); // Skip header line
            int lineNumber = 1;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] values = line.split(",");

                if (values.length != 6) {
                    ErrorHandling.handleInvalidColumnCountError(lineNumber, line);
                    continue;
                }

                try {
                    String date = values[0];
                    String sensorType = values[1];

                    if (values[2].isEmpty()) {
                        ErrorHandling.handleProcessingError("Missing sensor value in line " + lineNumber + ": " + line);
                        continue;
                    }

                    double value = Double.parseDouble(values[2]);
                    String unit = values[3];
                    String locationId = values[4];
                    String month = values[5];

                    sensorDataList.add(new SensorData(date, sensorType, value, unit, locationId, month));
                } catch (NumberFormatException e) {
                    ErrorHandling.handleProcessingError("Invalid number format in line " + lineNumber + ": " + line);
                }
            }
        } catch (IOException e) {
            ErrorHandling.handleFileNotFoundError(filePath);
            throw e;
        }
        return sensorDataList;
    }

    public Map<String, ThresholdData> readThresholds(String thresholdFile) throws IOException {
        Map<String, ThresholdData> thresholds = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/" + thresholdFile))) {
            String line;
            br.readLine(); // Skip header line
            int lineNumber = 1;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] values = line.split(",");

                if (values.length != 3) {
                    ErrorHandling.handleInvalidColumnCountError(lineNumber, line);
                    continue;
                }

                try {
                    String sensorType = values[0];
                    double minThreshold = Double.parseDouble(values[1]);
                    double maxThreshold = Double.parseDouble(values[2]);

                    thresholds.put(sensorType, new ThresholdData(sensorType, minThreshold, maxThreshold));
                } catch (NumberFormatException e) {
                    ErrorHandling.handleProcessingError("Invalid number format in line " + lineNumber + ": " + line);
                }
            }
        } catch (IOException e) {
            ErrorHandling.handleFileNotFoundError(thresholdFile);
            throw e;
        }
        return thresholds;
    }
}