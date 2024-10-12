package org.traccar.api.resource.web.mapper;

import org.traccar.api.resource.web.models.dto.VehicleInfoDTO;

public class VehicleMapper {

    private static final double SPEED_CONVERSION_FACTOR = 1.852;

    public static VehicleInfoDTO toVehicleInfoDTO(String deviceName, String protocol, double speedInKnots) {
        // Convertimos la velocidad de nudos a km/h
        double speedKmh = speedInKnots * SPEED_CONVERSION_FACTOR;

        // Creamos el DTO con los datos
        VehicleInfoDTO dto = new VehicleInfoDTO();
        dto.setVehicleId(deviceName); // Establece el nombre del vehículo
        dto.setProtocol(protocol); // Establece el protocolo
        dto.setSpeed(speedKmh); // Establece la velocidad en km/h

        return dto;
    }
}