package org.traccar.api.resource.web.services;

import jakarta.ws.rs.NotFoundException;
import org.traccar.api.resource.web.dao.DeviceDao;
import org.traccar.api.resource.web.models.dto.VehicleInfoDTO;
import org.traccar.model.Device;
import org.traccar.model.Position;
import org.traccar.storage.StorageException;

import jakarta.inject.Inject;
import java.util.List;

public class VehicleInfoService {

    @Inject
    private DeviceDao deviceDao;

    public VehicleInfoDTO getVehicleInfo(long deviceId) throws StorageException {
        // 1. Buscar el dispositivo
        Device device = deviceDao.getDeviceById(deviceId);
        if (device == null) {
            throw new NotFoundException("Device not found for ID: " + deviceId);
        }

        // 2. Buscar las posiciones más recientes
        List<Position> positions = deviceDao.getLatestPositionsByDeviceId(deviceId);
        if (positions == null || positions.isEmpty()) {
            throw new NotFoundException("No positions found for device ID: " + deviceId);
        }

        // 3. Obtener la última posición y convertir la velocidad a km/h
        Position lastPosition = positions.get(0);
        double speedKmh = lastPosition.getSpeed() * 1.852;

        // 4. Crear y devolver el DTO
        return new VehicleInfoDTO(
                device.getName(),                // vehicle_id
                lastPosition.getProtocol(),      // Protocolo
                speedKmh                         // Velocidad en km/h
        );
    }
}