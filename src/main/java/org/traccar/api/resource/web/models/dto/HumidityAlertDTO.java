package org.traccar.api.resource.web.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HumidityAlertDTO {
    @JsonProperty("vehicle_id")
    private String vehicleId;

    @JsonProperty("alert_type")
    private String alertType;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("type")
    private String type;

    @JsonProperty("set_point")
    private double setPoint;

    @JsonProperty("measurement")
    private double measurement;

    @JsonProperty("location_url")
    private String locationUrl;



    public HumidityAlertDTO(String vehicleId, String alertType, String timestamp, String type, double setPoint, double measurement, String locationUrl) {
        this.vehicleId = vehicleId;
        this.alertType = alertType;
        this.timestamp = timestamp;
        this.type = type;
        this.setPoint = setPoint;
        this.measurement = measurement;
        this.locationUrl = locationUrl;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public HumidityAlertDTO setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public String getAlertType() {
        return alertType;
    }

    public HumidityAlertDTO setAlertType(String alertType) {
        this.alertType = alertType;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public HumidityAlertDTO setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getType() {
        return type;
    }

    public HumidityAlertDTO setType(String type) {
        this.type = type;
        return this;
    }

    public double getSetPoint() {
        return setPoint;
    }

    public HumidityAlertDTO setSetPoint(double setPoint) {
        this.setPoint = setPoint;
        return this;
    }

    public double getMeasurement() {
        return measurement;
    }

    public HumidityAlertDTO setMeasurement(double measurement) {
        this.measurement = measurement;
        return this;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    public HumidityAlertDTO setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
        return this;
    }
}
