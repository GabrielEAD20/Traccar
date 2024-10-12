package org.traccar.api.resource.web.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.traccar.api.resource.web.models.dto.HumidityAlertDTO;
import org.traccar.api.resource.web.services.HumidityAlertService;
import org.traccar.storage.StorageException;

@Path("/humidity-alert")
@Produces(MediaType.APPLICATION_JSON)
public class HumidityAlertResource {

    @Inject
    private HumidityAlertService humidityAlertService;

    @GET
    @Path("/{id}")
    public HumidityAlertDTO getHumidityAlert(@PathParam("id") long deviceId) throws StorageException {
        // Delegar la lógica al servicio
        return humidityAlertService.getHumidityAlert(deviceId);
    }
}
