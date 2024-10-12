package org.traccar.api.resource.web.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.traccar.api.resource.web.models.dto.VehicleStatusNavBarDTO;
import org.traccar.model.Device;
import org.traccar.model.Position;
import org.traccar.storage.StorageException;
import org.traccar.api.resource.web.dao.DeviceDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Path("/vehicle-status-nav-bar")
@Produces(MediaType.APPLICATION_JSON)
public class VehicleStatusResourceNavBar {

    @Inject
    private DeviceDao deviceDao;

    @GET
    @Path("/{id}")
    public VehicleStatusNavBarDTO getVehicleStatus(@PathParam("id") long deviceId) throws StorageException {
        // Obtener el dispositivo por su ID
        Device device = deviceDao.getDeviceById(deviceId);

        // Verificar si el dispositivo existe
        if (device == null) {
            throw new NotFoundException("No se encontró el dispositivo con ID: " + deviceId);
        }

        // Obtener las posiciones más recientes del dispositivo
        List<Position> positions = deviceDao.getLatestPositionsByDeviceId(deviceId);

        // Verificar si hay posiciones disponibles
        if (!positions.isEmpty()) {
            // Obtener la posición más reciente
            Position latestPosition = positions.get(0);
            // Mapear la posición a un DTO que se enviará como respuesta
            return mapToVehicleStatusNavBarDTO(latestPosition, device.getName());
        } else {
            // Si no hay datos, lanzar una excepción indicando que no se encontraron posiciones
            throw new NotFoundException("No se encontraron posiciones para el dispositivo con id: " + deviceId);
        }
    }

    // Método para mapear la información de la posición a un DTO con el formato esperado
    private VehicleStatusNavBarDTO mapToVehicleStatusNavBarDTO(Position position, String deviceName) {
        // Obtener el número de satélites visibles desde los atributos
        int satellitesVisible = position.getAttributes().containsKey(Position.KEY_SATELLITES)
                ? (Integer) position.getAttributes().get(Position.KEY_SATELLITES)
                : 0;

        // Convertir la velocidad de nudos a km/h y redondearla a dos decimales
        double speedKph = Math.round((position.getSpeed() * 1.852) * 100.0) / 100.0;

        // Considerar que el vehículo está en movimiento si la velocidad es mayor a 0
        boolean moving = speedKph > 0;

        // Determinar si el motor está encendido en función de la entrada "input"
        boolean engineOn = position.getAttributes().containsKey("input") &&
                position.getAttributes().get("input").equals("1");

        // Calcular la diferencia de tiempo desde la última detección GPS
        String lastGpsDetection = calculateLastGpsDetection(position.getDeviceTime());

        // Determinar si el dispositivo está online basándose en si los datos están desactualizados o no
        boolean online = !position.getOutdated();

        // Determinar si el seguimiento GPS está activo
        boolean gpsTracking = position.getValid() && satellitesVisible > 0 &&
                position.getAttributes().containsKey("hdop") &&
                (Double) position.getAttributes().get("hdop") < 1.0 && !position.getOutdated();

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
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(position.getDeviceTime()) // Timestamp de la última posición
        );
    }

    // Método para calcular el tiempo desde la última detección GPS
    private String calculateLastGpsDetection(Date deviceTime) {
        // Obtener el tiempo actual en milisegundos
        long currentTime = System.currentTimeMillis();
        // Convertir el tiempo del dispositivo a milisegundos
        long deviceTimeMillis = deviceTime.getTime();
        // Calcular la diferencia de tiempo en segundos
        long differenceInSeconds = (currentTime - deviceTimeMillis) / 1000;

        // Si la diferencia es menor a un minuto, devolver el tiempo en segundos
        if (differenceInSeconds < 60) {
            return differenceInSeconds + " seconds ago";
        }
        // Si la diferencia es mayor a un minuto, devolver el tiempo en minutos
        long differenceInMinutes = differenceInSeconds / 60;
        return differenceInMinutes + " minutes ago";
    }
}
