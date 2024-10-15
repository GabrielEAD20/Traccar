package org.traccar.api.resource.web.services;

import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.traccar.api.resource.web.dao.DeviceDao;
import org.traccar.api.resource.web.dao.PositionDao;
import org.traccar.api.resource.web.models.dto.HumidityAlertDTO;
import org.traccar.model.Device;
import org.traccar.model.Position;
import org.traccar.storage.Storage;
import org.traccar.storage.StorageException;
import org.traccar.storage.query.Columns;
import org.traccar.storage.query.Condition;
import org.traccar.storage.query.Request;

import java.text.SimpleDateFormat;
import java.util.List;

public class HumidityAlertService {

    @Inject
    private Storage storage;

    @Inject
    private DeviceDao deviceDao;

    @Inject
    private PositionDao positionDao;

    public HumidityAlertDTO getHumidityAlert(long deviceId) throws StorageException {
        // Obtén la última posición del dispositivo usando su deviceId
        List<Position> positions = positionDao.getLatestPositionsByDeviceId(deviceId);


        for (Position position: positions){
            System.out.println(position.toString());
        }
        // Si hay posiciones, selecciona la más reciente
        if (!positions.isEmpty()) {
            Position latestPosition = positions.get(0);
            return mapToHumidityAlertDTO(latestPosition);
        } else {
            throw new NotFoundException("No se encontraron posiciones para el dispositivo con id: " + deviceId);
        }
    }

    private HumidityAlertDTO mapToHumidityAlertDTO(Position position) {
        // Obtener la humedad y otros valores relevantes desde los atributos
        double humidity = position.getAttributes().containsKey("humidity")
                ? ((Number) position.getAttributes().get("humidity")).doubleValue()
                : 0.0;

        String locationUrl = String.format(
                "https://maps.app.goo.gl/?q=%f,%f",
                position.getLatitude(), position.getLongitude()
        );

        return new HumidityAlertDTO(
                "A3K-459",  // Aquí puedes obtener el nombre del vehículo si es necesario
                "High Humidity",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(position.getDeviceTime()),
                "Humidity",
                85.0,  // Set point de la humedad
                humidity,  // Valor de la humedad actual
                locationUrl  // URL de la ubicación
        );
    }
}
