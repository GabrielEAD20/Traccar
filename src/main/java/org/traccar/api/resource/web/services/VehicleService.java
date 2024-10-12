package org.traccar.api.resource.web.services;

import jakarta.ws.rs.NotFoundException;
import org.traccar.api.resource.web.config.MessageConstants;
import org.traccar.api.resource.web.dao.DeviceDao;
import org.traccar.api.resource.web.dao.PositionDao;
import org.traccar.api.resource.web.mapper.VehicleMapper;
import org.traccar.api.resource.web.models.dto.VehicleInfoDTO;
import org.traccar.api.resource.web.util.ValidationUtil;
import org.traccar.model.Device;
import org.traccar.model.Position;
import org.traccar.storage.StorageException;

import jakarta.inject.Inject;
import java.util.List;


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
}