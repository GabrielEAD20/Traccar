package org.traccar.api.resource.web.mapper;

import org.traccar.api.resource.web.models.dto.VehicleInfoDTO;
import org.traccar.api.resource.web.models.dto.VehicleStatusDTO;
import org.traccar.api.resource.web.models.dto.VehicleStatusNavBarDTO;

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

    public static VehicleStatusNavBarDTO mapToVehicleStatusNavBarDTO(
            String deviceName,
            int satellitesVisible,
            double speedKph,
            boolean moving,
            boolean engineOn,
            String lastGpsDetection,
            boolean online,
            boolean gpsTracking,
            String deviceTimeFormatted) {

        // Retornar un DTO con todos los datos mapeados
        return new VehicleStatusNavBarDTO(
                deviceName,                    // Nombre del dispositivo (ID del vehículo)
                engineOn,                      // Estado del motor
                gpsTracking,                   // Seguimiento GPS activo o no
                moving,                        // Estado de movimiento
                satellitesVisible,             // Número de satélites visibles
                lastGpsDetection,              // Última detección GPS
                online,                        // Estado en línea (basado en si los datos están actualizados)
                true,                          // Seguimiento de ruta habilitado
                "Track unit on map",           // Mensaje de seguimiento
                "Show quick track",            // Mensaje de seguimiento rápido
                true,                          // Disponibilidad de mensajes de solicitud
                speedKph,                      // Velocidad en km/h
                deviceTimeFormatted            // Timestamp de la última posición
        );
    }

}