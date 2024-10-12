package org.traccar.api.resource.web.util;

import org.traccar.model.Device;
import org.traccar.model.Position;

import java.util.List;

public class ValidationUtil {

    // Valida que el dispositivo no sea nulo
    public static void validateDevice(Device device) {
        if (device == null) {
            throw new IllegalArgumentException("Device cannot be null.");
        }
    }

    // Valida que la lista de posiciones no sea nula ni vacía
    public static void validatePositions(List<Position> positions) {
        if (positions == null || positions.isEmpty()) {
            throw new IllegalArgumentException("No positions found for the device.");
        }
    }

    // Valida una posición específica
    public static void validatePosition(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null.");
        }
    }
}