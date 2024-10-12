package org.traccar.api.resource.web.dao;

import jakarta.inject.Inject;
import org.traccar.model.Device;
import org.traccar.model.Position;
import org.traccar.storage.Storage;
import org.traccar.storage.StorageException;
import org.traccar.storage.query.Columns;
import org.traccar.storage.query.Condition;
import org.traccar.storage.query.Request;

import java.util.List;

public class DeviceDao {

    @Inject
    private Storage storage;

    public Device getDeviceById(long deviceId) throws StorageException {
        return storage.getObject(Device.class, new Request(
                new Columns.All(), new Condition.Equals("id", deviceId)));
    }

    public List<Position> getLatestPositionsByDeviceId(long deviceId) throws StorageException {
        return storage.getObjects(Position.class, new Request(
                new Columns.All(), new Condition.LatestPositions(deviceId)));
    }
}