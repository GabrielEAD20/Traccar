package org.traccar.api.resource.web.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleInfoDTO {
    @JsonProperty("vehicle_id")
    private String vehicleId;

    @JsonProperty("protocol")
    private String protocol;

    @JsonProperty("speed")
    private double speed;

    public VehicleInfoDTO(String vehicleId, String protocol, double speed) {
        this.vehicleId = vehicleId;
        this.protocol = protocol;
        this.speed = speed;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
