package org.traccar.api.resource.web.util;

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

}
