package main.java.com.atyeti.csvreport.processing;

import main.java.com.atyeti.csvreport.model.SensorData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessingModule {

    public Map<String, Map<String, Double[]>> calculateMonthlyStats(List<SensorData> sensorDataList) {
        Map<String, Map<String, Double[]>> monthlyStats = new HashMap<>();

        for (SensorData data : sensorDataList) {
            monthlyStats.putIfAbsent(data.getSensorType(), new HashMap<>());
            Map<String, Double[]> stats = monthlyStats.get(data.getSensorType());

            stats.putIfAbsent(data.getMonth(), new Double[]{0.0, 0.0, Double.MIN_VALUE, Double.MAX_VALUE});
            updateStats(stats.get(data.getMonth()), data.getValue());
        }

        return monthlyStats;
    }

    private void updateStats(Double[] currentStats, double value) {
        currentStats[0] += value;
        currentStats[1] += 1;
        currentStats[2] = Math.max(currentStats[2], value);
        currentStats[3] = Math.min(currentStats[3], value);
    }
}
