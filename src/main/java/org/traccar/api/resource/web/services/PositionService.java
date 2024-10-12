package org.traccar.api.resource.web.services;

import jakarta.inject.Inject;
import org.traccar.api.resource.web.dao.DeviceDao;
import org.traccar.model.Position;
import org.traccar.storage.Storage;
import org.traccar.storage.StorageException;
import org.traccar.storage.query.Columns;
import org.traccar.storage.query.Condition;
import org.traccar.storage.query.Request;

import java.util.List;

public class PositionService {
    @Inject
    DeviceDao deviceDao;

    @Inject
    Storage storage;


    public List<Position> getLatestPositionsByDeviceId(long deviceId) throws StorageException {
        return storage.getObjects(Position.class, new Request(
                new Columns.All(), new Condition.LatestPositions(deviceId)));
    }
}
