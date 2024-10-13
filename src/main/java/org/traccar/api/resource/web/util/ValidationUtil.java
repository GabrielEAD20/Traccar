package org.traccar.api.resource.web.util;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

public class ValidationUtil {

    // Validación genérica para listas
    public static <T> List<T> requireNonEmptyList(List<T> list, String errorMessage) {
        if (list == null || list.isEmpty()) {
            throw new NotFoundException(errorMessage);
        }
        return list;
    }

    // Validación genérica para objetos no nulos
    public static <T> T requireNonNull(T object, String errorMessage) {
        if (object == null) {
            throw new NotFoundException(errorMessage);
        }
        return object;
    }
    public static void validateDeviceId(Long value) {
        if (value == null || value <= 0) {
            throw new BadRequestException(" cannot be null or non-positive.");
        }
    }
}
