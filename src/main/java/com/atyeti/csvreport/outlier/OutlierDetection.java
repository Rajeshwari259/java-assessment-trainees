package main.java.com.atyeti.csvreport.outlier;
import  main.java.com.atyeti.csvreport.errorhandling.ErrorHandling;
import  main.java.com.atyeti.csvreport.model.SensorData;
import  main.java.com.atyeti.csvreport.model.ThresholdData;



import java.util.*;

public class OutlierDetection {


    public Map<String, List<SensorData>> detectOutliers(List<SensorData> sensorDataList, Map<String, ThresholdData> thresholds) {
        Map<String, List<SensorData>> outliers = new HashMap<>();

        for (SensorData data : sensorDataList) {
            ThresholdData threshold = thresholds.get(data.getSensorType());

            if (threshold == null) {

                ErrorHandling.handleThresholdNotDefined(data.getSensorType());
                continue;
            }


            if (data.getValue() < threshold.getMinThreshold() || data.getValue() > threshold.getMaxThreshold()) {
                outliers.computeIfAbsent(data.getSensorType(), _ -> new ArrayList<>()).add(data);
            }
        }
        return outliers;
    }

}