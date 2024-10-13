package org.traccar.api.resource.web.api;


import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.traccar.api.resource.web.config.VersionApiConstant;
import org.traccar.api.resource.web.models.dto.VehicleInfoDTO;
import org.traccar.api.resource.web.models.dto.VehicleStatusDTO;
import org.traccar.api.resource.web.services.VehicleService;
import org.traccar.storage.StorageException;

@Path(VersionApiConstant.API_VERSION_V1   + "/vehicle")
@Produces(MediaType.APPLICATION_JSON)
public class VehicleResource {

    @Inject
    private VehicleService vehicleService;

    @GET
    @Path("/info-basic/{device-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public VehicleInfoDTO getVehicleInfo(@PathParam("device-id") long deviceId) throws StorageException {
        return vehicleService.getVehicleInfoBasic(deviceId);
    }

    @GET
    @Path("/status-nav-bar/{device-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public VehicleStatusDTO getStatusDeviceForNavBar(@PathParam("device-id") long deviceId) throws StorageException {
        return vehicleService.getVehicleStatus(deviceId);
    }

}
