package org.traccar.api.resource.web.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleStatusNavBarDTO {
    @JsonProperty("vehicle_id")
    private String vehicleId;

    @JsonProperty("engine_on")
    private boolean engineOn;

    @JsonProperty("gps_tracking")
    private boolean gpsTracking;

    @JsonProperty("moving")
    private boolean moving;

    @JsonProperty("satellites_visible")
    private int satellitesVisible;

    @JsonProperty("last_gps_detection")
    private String lastGpsDetection;

    @JsonProperty("online")
    private boolean online;

    @JsonProperty("route_tracking_enabled")
    private boolean routeTrackingEnabled;

    @JsonProperty("track_on_map")
    private String trackOnMap;

    @JsonProperty("quick_track_message")
    private String quickTrackMessage;

    @JsonProperty("message_request_available")
    private boolean messageRequestAvailable;

    @JsonProperty("speed_kph")
    private double speedKph;

    @JsonProperty("timestamp")
    private String timestamp;

    public VehicleStatusNavBarDTO(String vehicleId, boolean engineOn, boolean gpsTracking, boolean moving, int satellitesVisible, String lastGpsDetection, boolean online, boolean routeTrackingEnabled, String trackOnMap, String quickTrackMessage, boolean messageRequestAvailable, double speedKph, String timestamp) {
        this.vehicleId = vehicleId;
        this.engineOn = engineOn;
        this.gpsTracking = gpsTracking;
        this.moving = moving;
        this.satellitesVisible = satellitesVisible;
        this.lastGpsDetection = lastGpsDetection;
        this.online = online;
        this.routeTrackingEnabled = routeTrackingEnabled;
        this.trackOnMap = trackOnMap;
        this.quickTrackMessage = quickTrackMessage;
        this.messageRequestAvailable = messageRequestAvailable;
        this.speedKph = speedKph;
        this.timestamp = timestamp;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public VehicleStatusNavBarDTO setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public boolean isEngineOn() {
        return engineOn;
    }

    public VehicleStatusNavBarDTO setEngineOn(boolean engineOn) {
        this.engineOn = engineOn;
        return this;
    }

    public boolean isGpsTracking() {
        return gpsTracking;
    }

    public VehicleStatusNavBarDTO setGpsTracking(boolean gpsTracking) {
        this.gpsTracking = gpsTracking;
        return this;
    }

    public boolean isMoving() {
        return moving;
    }

    public VehicleStatusNavBarDTO setMoving(boolean moving) {
        this.moving = moving;
        return this;
    }

    public int getSatellitesVisible() {
        return satellitesVisible;
    }

    public VehicleStatusNavBarDTO setSatellitesVisible(int satellitesVisible) {
        this.satellitesVisible = satellitesVisible;
        return this;
    }

    public String getLastGpsDetection() {
        return lastGpsDetection;
    }

    public VehicleStatusNavBarDTO setLastGpsDetection(String lastGpsDetection) {
        this.lastGpsDetection = lastGpsDetection;
        return this;
    }

    public boolean isOnline() {
        return online;
    }

    public VehicleStatusNavBarDTO setOnline(boolean online) {
        this.online = online;
        return this;
    }

    public boolean isRouteTrackingEnabled() {
        return routeTrackingEnabled;
    }

    public VehicleStatusNavBarDTO setRouteTrackingEnabled(boolean routeTrackingEnabled) {
        this.routeTrackingEnabled = routeTrackingEnabled;
        return this;
    }

    public String getTrackOnMap() {
        return trackOnMap;
    }

    public VehicleStatusNavBarDTO setTrackOnMap(String trackOnMap) {
        this.trackOnMap = trackOnMap;
        return this;
    }

    public String getQuickTrackMessage() {
        return quickTrackMessage;
    }

    public VehicleStatusNavBarDTO setQuickTrackMessage(String quickTrackMessage) {
        this.quickTrackMessage = quickTrackMessage;
        return this;
    }

    public boolean isMessageRequestAvailable() {
        return messageRequestAvailable;
    }

    public VehicleStatusNavBarDTO setMessageRequestAvailable(boolean messageRequestAvailable) {
        this.messageRequestAvailable = messageRequestAvailable;
        return this;
    }

    public double getSpeedKph() {
        return speedKph;
    }

    public VehicleStatusNavBarDTO setSpeedKph(double speedKph) {
        this.speedKph = speedKph;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public VehicleStatusNavBarDTO setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
