package main.java.com.atyeti.csvreport.errorhandling;

import java.util.logging.Logger;

public class ErrorHandling {
    private static final Logger logger = Logger.getLogger(ErrorHandling.class.getName());

    // Enum for error codes for better structure and scalability
    public enum ErrorCode {
        FILE_NOT_FOUND("ERR001", "File not found"),
        INVALID_COLUMN_COUNT("ERR002", "Invalid data format"),
        PROCESSING_ERROR("ERR003", "Processing error"),
        THRESHOLDS_NOT_DEFINED("ERR004", "Thresholds not defined");

        private final String code;
        private final String message;

        ErrorCode(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    // Centralized error message handling with specific error levels
    public static void logError(ErrorCode errorCode, String details) {
        String logMessage = String.format("%s - %s: %s", errorCode.getCode(), errorCode.getMessage(), details);
        // Log the error as severe, but you can change the level based on severity
        logger.severe(logMessage);
    }

    // Convenience methods for specific error scenarios
    public static void handleFileNotFoundError(String fileName) {
        logError(ErrorCode.FILE_NOT_FOUND, "File not found: " + fileName);
    }

    public static void handleInvalidColumnCountError(int lineNumber, String lineContent) {
        logError(ErrorCode.INVALID_COLUMN_COUNT, "Line " + lineNumber + ": " + lineContent);
    }

    public static void handleProcessingError(String errorMessage) {
        logError(ErrorCode.PROCESSING_ERROR, errorMessage);
    }

    public static void handleThresholdNotDefined(String sensorType) {
        logError(ErrorCode.THRESHOLDS_NOT_DEFINED, "No thresholds defined for sensor type: " + sensorType);
    }
}
