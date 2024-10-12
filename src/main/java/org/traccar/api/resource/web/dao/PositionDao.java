package org.traccar.api.resource.web.dao;

import jakarta.inject.Inject;
import org.traccar.model.Position;
import org.traccar.storage.Storage;
import org.traccar.storage.StorageException;
import org.traccar.storage.query.Columns;
import org.traccar.storage.query.Condition;
import org.traccar.storage.query.Request;

import java.util.List;

public class PositionDao {

    @Inject
    private Storage storage;

    public List<Position> getPositionsByDeviceId(long deviceId) throws StorageException {
        // Acceso a la base de datos para obtener las posiciones según el deviceId
        return storage.getObjects(Position.class, new Request(
                new Columns.All(), new Condition.Equals("deviceId", deviceId)));
    }

}
