package org.traccar.api.resource.web.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.traccar.api.resource.web.config.VersionApiConstant;
import org.traccar.api.resource.web.models.dto.HumidityAlertDTO;
import org.traccar.api.resource.web.models.dto.VehicleInfoDTO;
import org.traccar.api.resource.web.models.dto.VehicleStatusDTO;
import org.traccar.api.resource.web.models.dto.VehicleStatusNavBarDTO;
import org.traccar.api.resource.web.services.VehicleService;
import org.traccar.api.resource.web.util.ValidationUtil;
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
        // Validar el deviceId
        ValidationUtil.validateDeviceId(deviceId);

        return vehicleService.getVehicleInfoBasic(deviceId);
    }

    @GET
    @Path("/info-advanced/{device-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public VehicleStatusDTO getStatusDevice(@PathParam("device-id") long deviceId) throws StorageException {
        // Validar el deviceId
        ValidationUtil.validateDeviceId(deviceId);

        return vehicleService.getVehicleStatus(deviceId);
    }

    @GET
    @Path("/nav-bar/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public VehicleStatusNavBarDTO getVehicleStatusForNavBar(@PathParam("id") long deviceId) throws StorageException {
        // Validar el deviceId
        ValidationUtil.validateDeviceId(deviceId);

        return vehicleService.getVehicleNavBarStatus(deviceId);
    }

    @GET
    @Path("/info-humidity/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public HumidityAlertDTO getInfoHumidity(@PathParam("id") long deviceId) throws StorageException {
        // Validar el deviceId
        ValidationUtil.validateDeviceId(deviceId);

        return vehicleService.getHumidityAlert(deviceId);
    }
}
