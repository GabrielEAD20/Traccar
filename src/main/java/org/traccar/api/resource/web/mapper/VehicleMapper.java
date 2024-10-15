package org.traccar.api.resource.web.mapper;

import org.traccar.api.resource.web.models.dto.HumidityAlertDTO;
import org.traccar.api.resource.web.models.dto.VehicleInfoDTO;
import org.traccar.api.resource.web.models.dto.VehicleStatusDTO;
import org.traccar.api.resource.web.models.dto.VehicleStatusNavBarDTO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class VehicleMapper {

    private static final double SPEED_CONVERSION_FACTOR = 1.852;

    // Método para mapear VehicleInfoDTO
    public static VehicleInfoDTO toVehicleInfoDTO(String deviceName, String protocol, double speedInKnots) {
        // Asignación de variables
        double speedKmh = speedInKnots * SPEED_CONVERSION_FACTOR;

        // Crear el DTO con las variables asignadas
        VehicleInfoDTO dto = new VehicleInfoDTO();
        dto.setVehicleId(deviceName);
        dto.setProtocol(protocol);
        dto.setSpeed(speedKmh);

        return dto;
    }

    // Método para mapear VehicleStatusDTO
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
        // Asignación de variables
        String vehicleId = "A3K-459"; // Ejemplo de asignación
        boolean moving = speedKmh > 0;

        // Crear el DTO utilizando las variables
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

    // Método para mapear VehicleStatusNavBarDTO
    public static VehicleStatusNavBarDTO mapToVehicleStatusNavBarDTO(
            String deviceName,
            int satellitesVisible,
            double speedKph,
            boolean moving,
            boolean engineOn,
            String lastGpsDetection,
            boolean online,
            boolean gpsTracking,
            String deviceTimeFormatted
    ) {
        // Asignación de variables
        boolean trackingEnabled = true;
        String trackUnitMessage = "Track unit on map";
        String quickTrackMessage = "Show quick track";
        boolean messagesAvailable = true;

        // Crear el DTO utilizando las variables
        return new VehicleStatusNavBarDTO(
                deviceName,
                engineOn,
                gpsTracking,
                moving,
                satellitesVisible,
                lastGpsDetection,
                online,
                trackingEnabled,
                trackUnitMessage,
                quickTrackMessage,
                messagesAvailable,
                speedKph,
                deviceTimeFormatted
        );
    }

    // Método para mapear HumidityAlertDTO
    public static HumidityAlertDTO mapToHumidityAlertDTO(
            String nameDevice, double latitude,
            double longitude,
            double humidity,
            Date deviceTime
    ) {
        //TODO
        // Asignación de variables
        String vehicleId = nameDevice;
        String alertType = "High Humidity";
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(deviceTime);
        String type = "Humidity";
        double setPoint = 85.0;
        String locationUrl = String.format("https://maps.app.goo.gl/?q=%f,%f", latitude, longitude);

        // Crear el DTO utilizando las variables
        return new HumidityAlertDTO(
                vehicleId,
                alertType,
                timestamp,
                type,
                setPoint,
                humidity,
                locationUrl
        );
    }
}
