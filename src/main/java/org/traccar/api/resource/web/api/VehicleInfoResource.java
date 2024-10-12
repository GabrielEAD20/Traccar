package org.traccar.api.resource.web.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.traccar.api.resource.web.models.dto.VehicleInfoDTO;
import org.traccar.api.resource.web.services.VehicleService;
import org.traccar.api.security.PermissionsService;
import org.traccar.model.Device;
import org.traccar.storage.StorageException;

@Path("/vehicle-info")
public class VehicleInfoResource {

    @Inject
    private VehicleService vehicleService;

    @Inject
    private PermissionsService permissionsService;

    @GET
    @Path("/{device-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public VehicleInfoDTO getVehicleInfo(@PathParam("device-id") long deviceId) throws StorageException {
        // Obtener la información del vehículo usando el servicio
        return vehicleService.getVehicleInfoBasic(deviceId);
    }

    private long getUserId() {
        return 1; // Ejemplo estático
    }
}
