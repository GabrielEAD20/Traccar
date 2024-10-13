package org.traccar.api.resource.web.mapper;

import org.traccar.api.resource.web.models.dto.VehicleInfoDTO;
import org.traccar.api.resource.web.models.dto.VehicleStatusDTO;

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
    public static VehicleStatusDTO mapToVehicleStatusDTO(
            double latitude,
            double longitude,
            double speedKmh,
            String protocol,
            String address,
            double odometer,
            int satellites,
            boolean valid,
            double signalStrength,
            double temperature,
            boolean doorStatus,
            boolean engineStatus,
            boolean vehicleState,
            String deviceTime
    ) {
        return new VehicleStatusDTO(
                vehicleState,
                engineStatus,
                speedKmh,
                address,
                latitude,
                longitude,
                odometer,
                deviceTime,
                satellites,
                valid,
                signalStrength,
                temperature,
                75.0, // SIN DATOS
                doorStatus
        );
    }

}