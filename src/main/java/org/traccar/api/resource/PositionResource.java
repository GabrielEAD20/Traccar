/*
 * Copyright 2015 - 2022 Anton Tananaev (anton@traccar.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.traccar.api.resource;

import org.traccar.api.BaseResource;
import org.traccar.helper.model.PositionUtil;
import org.traccar.model.Device;
import org.traccar.model.Position;
import org.traccar.model.UserRestrictions;
import org.traccar.reports.CsvExportProvider;
import org.traccar.reports.GpxExportProvider;
import org.traccar.reports.KmlExportProvider;
import org.traccar.storage.StorageException;
import org.traccar.storage.query.Columns;
import org.traccar.storage.query.Condition;
import org.traccar.storage.query.Request;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;

@Path("positions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PositionResource extends BaseResource {

    @Inject
    private KmlExportProvider kmlExportProvider;

    @Inject
    private CsvExportProvider csvExportProvider;

    @Inject
    private GpxExportProvider gpxExportProvider;

    @GET
    public Collection<Position> getJson(
            @QueryParam("deviceId") long deviceId, @QueryParam("id") List<Long> positionIds,
            @QueryParam("from") Date from, @QueryParam("to") Date to)
            throws StorageException {
        if (!positionIds.isEmpty()) {
            var positions = new ArrayList<Position>();
            for (long positionId : positionIds) {
                Position position = storage.getObject(Position.class, new Request(
                        new Columns.All(), new Condition.Equals("id", positionId)));
                permissionsService.checkPermission(Device.class, 1, position.getDeviceId());
                positions.add(position);
            }
            return positions;
        } else if (deviceId > 0) {
            permissionsService.checkPermission(Device.class, getUserId(), deviceId);
            if (from != null && to != null) {
                permissionsService.checkRestriction(getUserId(), UserRestrictions::getDisableReports);

                return PositionUtil.getPositions(storage, deviceId, from, to);
            } else {
                return storage.getObjects(Position.class, new Request(
                        new Columns.All(), new Condition.LatestPositions(deviceId)));
            }
        } else {
            return PositionUtil.getLatestPositions(storage, getUserId());
        }
    }


    @Path("hardcoded-positions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Position> getHardcodedPositions() {
        List<Position> positions = new ArrayList<>();

        // Crea objetos Position con valores "hardcodeados"
        Position position1 = new Position();
        position1.setId(4215406);
        position1.setDeviceId(83);
        position1.setProtocol("wialon");
        position1.setLatitude(-12.289331666666667);
        position1.setLongitude(-76.85273);
        position1.setSpeed(0);
        position1.setCourse(78);
        position1.setAltitude(43);
        position1.setServerTime(new Date());  // Pon la fecha actual o una fija
        position1.setDeviceTime(new Date());
        position1.setFixTime(new Date());
        position1.setValid(true);

        // Setea los atributos "hardcodeados"
        position1.getAttributes().put("sat", 19);
        position1.getAttributes().put("hdop", 0.6);
        position1.getAttributes().put("adc1", "0.00");
        position1.getAttributes().put("adc2", "3.91");
        position1.getAttributes().put("csq", 27);
        position1.getAttributes().put("motion", false);
        position1.getAttributes().put("totalDistance", 84083.82484069117);

        // Añade a la lista de posiciones
        positions.add(position1);

        // Puedes crear más posiciones si es necesario
        return positions;
    }
    @DELETE
    public Response remove(
            @QueryParam("deviceId") long deviceId,
            @QueryParam("from") Date from, @QueryParam("to") Date to) throws StorageException {
        permissionsService.checkPermission(Device.class, getUserId(), deviceId);
        permissionsService.checkRestriction(getUserId(), UserRestrictions::getReadonly);

        var conditions = new LinkedList<Condition>();
        conditions.add(new Condition.Equals("deviceId", deviceId));
        conditions.add(new Condition.Between("fixTime", "from", from, "to", to));
        storage.removeObject(Position.class, new Request(Condition.merge(conditions)));

        return Response.status(Response.Status.NO_CONTENT).build();
    }

    @Path("kml")
    @GET
    @Produces("application/vnd.google-earth.kml+xml")
    public Response getKml(
            @QueryParam("deviceId") long deviceId,
            @QueryParam("from") Date from, @QueryParam("to") Date to) throws StorageException {
        permissionsService.checkPermission(Device.class, getUserId(), deviceId);
        StreamingOutput stream = output -> {
            try {
                kmlExportProvider.generate(output, deviceId, from, to);
            } catch (StorageException e) {
                throw new WebApplicationException(e);
            }
        };
        return Response.ok(stream)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=positions.kml").build();
    }

    @Path("csv")
    @GET
    @Produces("text/csv")
    public Response getCsv(
            @QueryParam("deviceId") long deviceId,
            @QueryParam("from") Date from, @QueryParam("to") Date to) throws StorageException {
        permissionsService.checkPermission(Device.class, getUserId(), deviceId);
        StreamingOutput stream = output -> {
            try {
                csvExportProvider.generate(output, deviceId, from, to);
            } catch (StorageException e) {
                throw new WebApplicationException(e);
            }
        };
        return Response.ok(stream)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=positions.csv").build();
    }

    @Path("gpx")
    @GET
    @Produces("application/gpx+xml")
    public Response getGpx(
            @QueryParam("deviceId") long deviceId,
            @QueryParam("from") Date from, @QueryParam("to") Date to) throws StorageException {
        permissionsService.checkPermission(Device.class, getUserId(), deviceId);
        StreamingOutput stream = output -> {
            try {
                gpxExportProvider.generate(output, deviceId, from, to);
            } catch (StorageException e) {
                throw new WebApplicationException(e);
            }
        };
        return Response.ok(stream)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=positions.gpx").build();
    }

}
