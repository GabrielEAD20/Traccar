package org.traccar.api.resource.web.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.traccar.api.resource.web.models.dto.VehicleInfoDTO;
import org.traccar.api.resource.web.services.VehicleInfoService;
import org.traccar.api.security.PermissionsService;
import org.traccar.model.Device;
import org.traccar.storage.StorageException;

@Path("/vehicle-info")
public class VehicleInfoResource {

    @Inject
    private VehicleInfoService vehicleInfoService;

    @Inject
    private PermissionsService permissionsService;

    @GET
    @Path("/{device-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public VehicleInfoDTO getVehicleInfo(@PathParam("device-id") long deviceId) throws StorageException {
        // Verificar permisos de acceso
        permissionsService.checkPermission(Device.class, getUserId(), deviceId);
        // Obtener la información del vehículo usando el servicio
        return vehicleInfoService.getVehicleInfo(deviceId);
    }

    private long getUserId() {
        return 1; // Ejemplo estático
    }
}
