package org.traccar.api.resource.web.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleStatusDTO {
    @JsonProperty("vehicle_state")
    private boolean vehicleState;

    @JsonProperty("engine_status")
    private boolean engineStatus;

    @JsonProperty("speed")
    private double speed;

    @JsonProperty("address")
    private String address;

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;

    @JsonProperty("odometer")
    private double odometer;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("satellites")
    private int satellites;

    @JsonProperty("gps_status")
    private boolean gpsStatus;

    @JsonProperty("signal_strength")
    private double signalStrength;

    @JsonProperty("temperature")
    private double temperature;

    @JsonProperty("humidity")
    private double humidity;

    @JsonProperty("door_status")
    private boolean doorStatus;

    public VehicleStatusDTO(boolean vehicleState, boolean engineStatus, double speed, String address, double latitude, double longitude, double odometer, String timestamp, int satellites, boolean gpsStatus, double signalStrength, double temperature, double humidity, boolean doorStatus) {
        this.vehicleState = vehicleState;
        this.engineStatus = engineStatus;
        this.speed = speed;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.odometer = odometer;
        this.timestamp = timestamp;
        this.satellites = satellites;
        this.gpsStatus = gpsStatus;
        this.signalStrength = signalStrength;
        this.temperature = temperature;
        this.humidity = humidity;
        this.doorStatus = doorStatus;
    }


    public boolean isVehicleState() {
        return vehicleState;
    }

    public VehicleStatusDTO setVehicleState(boolean vehicleState) {
        this.vehicleState = vehicleState;
        return this;
    }

    public boolean isEngineStatus() {
        return engineStatus;
    }

    public VehicleStatusDTO setEngineStatus(boolean engineStatus) {
        this.engineStatus = engineStatus;
        return this;
    }

    public double getSpeed() {
        return speed;
    }

    public VehicleStatusDTO setSpeed(double speed) {
        this.speed = speed;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public VehicleStatusDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public VehicleStatusDTO setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public VehicleStatusDTO setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    public double getOdometer() {
        return odometer;
    }

    public VehicleStatusDTO setOdometer(double odometer) {
        this.odometer = odometer;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public VehicleStatusDTO setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public int getSatellites() {
        return satellites;
    }

    public VehicleStatusDTO setSatellites(int satellites) {
        this.satellites = satellites;
        return this;
    }

    public boolean isGpsStatus() {
        return gpsStatus;
    }

    public VehicleStatusDTO setGpsStatus(boolean gpsStatus) {
        this.gpsStatus = gpsStatus;
        return this;
    }

    public double getSignalStrength() {
        return signalStrength;
    }

    public VehicleStatusDTO setSignalStrength(double signalStrength) {
        this.signalStrength = signalStrength;
        return this;
    }

    public double getTemperature() {
        return temperature;
    }

    public VehicleStatusDTO setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public double getHumidity() {
        return humidity;
    }

    public VehicleStatusDTO setHumidity(double humidity) {
        this.humidity = humidity;
        return this;
    }

    public boolean isDoorStatus() {
        return doorStatus;
    }

    public VehicleStatusDTO setDoorStatus(boolean doorStatus) {
        this.doorStatus = doorStatus;
        return this;
    }
}