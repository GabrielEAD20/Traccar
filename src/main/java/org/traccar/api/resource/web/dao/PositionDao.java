package org.traccar.api.resource.web.dao;

import jakarta.inject.Inject;
import org.traccar.api.resource.web.config.MessageConstants;
import org.traccar.api.resource.web.util.ValidationUtil;
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
        List<Position> positions = storage.getObjects(Position.class, new Request(
                new Columns.All(), new Condition.Equals("deviceId", deviceId)));
        return ValidationUtil.requireNonEmptyList(positions, MessageConstants.ERROR_DEVICE_NOT_FOUND + deviceId);
    }

    public List<Position> getLatestPositionsByDeviceId(long deviceId) throws StorageException {
        List<Position> positions = storage.getObjects(Position.class, new Request(
                new Columns.All(), new Condition.LatestPositions(deviceId)));
        return ValidationUtil.requireNonEmptyList(positions, MessageConstants.ERROR_NO_LATEST_POSITIONS_FOUND + deviceId);
    }
}
