package org.traccar.api.resource.web.services;

import jakarta.ws.rs.NotFoundException;
import org.traccar.api.resource.web.config.MessageConstants;
import org.traccar.api.resource.web.dao.DeviceDao;
import org.traccar.api.resource.web.dao.PositionDao;
import org.traccar.api.resource.web.mapper.VehicleMapper;
import org.traccar.api.resource.web.models.dto.VehicleInfoDTO;
import org.traccar.api.resource.web.models.dto.VehicleStatusDTO;
import org.traccar.api.resource.web.models.dto.VehicleStatusNavBarDTO;
import org.traccar.api.resource.web.util.GpsUtils;
import org.traccar.api.resource.web.util.ValidationUtil;
import org.traccar.model.Device;
import org.traccar.model.Position;
import org.traccar.storage.StorageException;

import jakarta.inject.Inject;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


public class VehicleService {

    @Inject
    private DeviceDao deviceDao;

    @Inject
    private PositionDao positionDao;

    public VehicleInfoDTO getVehicleInfoBasic(long deviceId) throws StorageException {
        // 1. Validar y obtener el dispositivo
        Device device = deviceDao.getDeviceById(deviceId);
        // 2. Obtener y validar las posiciones más recientes
        List<Position> positions = positionDao.getLatestPositionsByDeviceId(deviceId);
        // 3. Obtener la última posición y convertir la velocidad a km/h
        Position lastPosition = positions.get(0);

        return VehicleMapper.toVehicleInfoDTO(
                device.getName(),
                lastPosition.getProtocol(),
                lastPosition.getSpeed());
    }


    public VehicleStatusDTO getVehicleStatus(long deviceId) throws StorageException {

        // 1. Obtener y validar las posiciones más recientes
        List<Position> positions = positionDao.getLatestPositionsByDeviceId(deviceId);
        Position lastPosition = positions.get(0);

        System.out.println(positions.get(0).toString());
        // 2. Extraer atributos
        Map<String, Object> attributes = lastPosition.getAttributes();
        double odometer = attributes.containsKey(Position.KEY_TOTAL_DISTANCE) ? (Double) attributes.get(Position.KEY_TOTAL_DISTANCE) : 0.0;
        int satellites = attributes.containsKey(Position.KEY_SATELLITES) ? (Integer) attributes.get(Position.KEY_SATELLITES) : 0;
        double signalStrength = attributes.containsKey("csq") ? ((Number) attributes.get("csq")).doubleValue() : 0.0;
        double temperature = attributes.containsKey("th_r1_t") ? ((Number) attributes.get("th_r1_t")).doubleValue() : 0.0;
        boolean doorStatus = checkDoorStatus(attributes);

        boolean engineStatus = attributes.containsKey("input") && "1".equals(attributes.get("input"));
        boolean vehicleState = attributes.containsKey("motion") ? (Boolean) attributes.get("motion") : false;
        double speedKmh = Math.round((lastPosition.getSpeed() * 1.852) * 100.0) / 100.0;

        // 3. Mapear y devolver el DTO
        return VehicleMapper.mapToVehicleStatusDTO(
                lastPosition.getLatitude(),
                lastPosition.getLongitude(),
                speedKmh,
                lastPosition.getProtocol(),
                lastPosition.getAddress(),
                odometer,
                satellites,
                lastPosition.getValid(),
                signalStrength,
                temperature,
                doorStatus,
                engineStatus,
                vehicleState,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(lastPosition.getDeviceTime())
        );
    }


    public VehicleStatusNavBarDTO getVehicleNavBarStatus(long deviceId) throws StorageException {
        // Obtener el dispositivo por su ID
        Device device = deviceDao.getDeviceById(deviceId);

        // Obtener las posiciones más recientes del dispositivo
        List<Position> positions = deviceDao.getLatestPositionsByDeviceId(deviceId);

        // Verificar si hay posiciones disponibles
            Position latestPosition = positions.get(0);
            Map<String, Object> attributes = latestPosition.getAttributes();

            // Extraer los valores necesarios de los atributos
            int satellitesVisible = attributes.containsKey(Position.KEY_SATELLITES)
                    ? (Integer) attributes.get(Position.KEY_SATELLITES)
                    : 0;

            double speedKph = Math.round((latestPosition.getSpeed() * 1.852) * 100.0) / 100.0;
            boolean moving = speedKph > 0;
            boolean engineOn = attributes.containsKey("input") && attributes.get("input").equals("1");
            String lastGpsDetection = GpsUtils.calculateLastGpsDetection(latestPosition.getDeviceTime());
            boolean online = !latestPosition.getOutdated();
            boolean gpsTracking = latestPosition.getValid() && satellitesVisible > 0 &&
                    attributes.containsKey("hdop") &&
                    (Double) attributes.get("hdop") < 1.0 && !latestPosition.getOutdated();
            String deviceTimeFormatted = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(latestPosition.getDeviceTime());

            // Mapear la posición a un DTO que se enviará como respuesta
            return VehicleMapper.mapToVehicleStatusNavBarDTO(
                    device.getName(),
                    satellitesVisible,
                    speedKph,
                    moving,
                    engineOn,
                    lastGpsDetection,
                    online,
                    gpsTracking,
                    deviceTimeFormatted
            );
    }

    public static boolean checkDoorStatus(Map<String, Object> attributes) {

        boolean doorL1 = attributes.containsKey("d_l1_s") && ((Number) attributes.get("d_l1_s")).intValue() == 1;
        boolean doorL2 = attributes.containsKey("d_l2_s") && ((Number) attributes.get("d_l2_s")).intValue() == 1;
        boolean doorL3 = attributes.containsKey("d_l3_s") && ((Number) attributes.get("d_l3_s")).intValue() == 1;
        boolean doorR1 = attributes.containsKey("d_r1_s") && ((Number) attributes.get("d_r1_s")).intValue() == 1;
        boolean doorR2 = attributes.containsKey("d_r2_s") && ((Number) attributes.get("d_r2_s")).intValue() == 1;
        boolean doorR3 = attributes.containsKey("d_r3_s") && ((Number) attributes.get("d_r3_s")).intValue() == 1;

        return doorL1 || doorL2 || doorL3 || doorR1 || doorR2 || doorR3;
    }

}