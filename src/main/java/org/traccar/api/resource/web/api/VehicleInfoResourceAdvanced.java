package org.traccar.api.resource.web.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.traccar.api.resource.web.models.dto.VehicleStatusDTO;
import org.traccar.api.resource.web.services.PositionService;
import org.traccar.model.Position;
import org.traccar.storage.Storage;
import org.traccar.storage.StorageException;
import org.traccar.storage.query.Columns;
import org.traccar.storage.query.Condition;
import org.traccar.storage.query.Request;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Path("/vehicle-info-advanced")
@Produces(MediaType.APPLICATION_JSON)
public class VehicleInfoResourceAdvanced {

    @Inject
    private Storage storage;

    @Inject
    private PositionService positionService;

    @GET
    @Path("/{id}")
    public VehicleStatusDTO getVehicleInfo(@PathParam("id") long deviceId) throws StorageException {
        List<Position> positions = positionService.getLatestPositionsByDeviceId(deviceId);

        if (!positions.isEmpty()) {
            Position latestPosition = positions.get(0);
            return mapToVehicleStatusDTO(latestPosition);
        } else {
            throw new NotFoundException("No se encontraron posiciones para el dispositivo con id: " + deviceId);
        }
    }

    private VehicleStatusDTO mapToVehicleStatusDTO(Position position) {
        Map<String, Object> attributes = position.getAttributes();

        double odometer = attributes.containsKey(Position.KEY_TOTAL_DISTANCE)
                ? (Double) attributes.get(Position.KEY_TOTAL_DISTANCE)
                : 0.0;

        int satellites = attributes.containsKey(Position.KEY_SATELLITES)
                ? (Integer) attributes.get(Position.KEY_SATELLITES)
                : 0;

        double signalStrength = attributes.containsKey("csq")
                ? ((Number) attributes.get("csq")).doubleValue()
                : 0.0;

        double temperature = attributes.containsKey("th_r1_t")
                ? ((Number) attributes.get("th_r1_t")).doubleValue()
                : 0.0;

        boolean doorStatus = checkDoorStatus(attributes);

        boolean engineStatus = attributes.containsKey("input")
                ? "1".equals(attributes.get("input"))
                : false;

        boolean vehicleState = attributes.containsKey("motion")
                ? (Boolean) attributes.get("motion")
                : false;

        double speedKmh = Math.round((position.getSpeed() * 1.852) * 100.0) / 100.0;

        return new VehicleStatusDTO(
                vehicleState,
                engineStatus,
                speedKmh,
                position.getAddress(),
                position.getLatitude(),
                position.getLongitude(),
                odometer,
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(position.getDeviceTime()),
                satellites,
                position.getValid(),
                signalStrength,
                temperature,
                75.0,
                doorStatus
        );
    }

    private boolean checkDoorStatus(Map<String, Object> attributes) {
        boolean doorL1 = attributes.containsKey("d_l1_s") && (Integer) attributes.get("d_l1_s") == 1;
        boolean doorL2 = attributes.containsKey("d_l2_s") && (Integer) attributes.get("d_l2_s") == 1;
        boolean doorL3 = attributes.containsKey("d_l3_s") && (Integer) attributes.get("d_l3_s") == 1;
        boolean doorR1 = attributes.containsKey("d_r1_s") && (Integer) attributes.get("d_r1_s") == 1;
        boolean doorR2 = attributes.containsKey("d_r2_s") && (Integer) attributes.get("d_r2_s") == 1;
        boolean doorR3 = attributes.containsKey("d_r3_s") && (Integer) attributes.get("d_r3_s") == 1;

        return doorL1 || doorL2 || doorL3 || doorR1 || doorR2 || doorR3;
    }
}
