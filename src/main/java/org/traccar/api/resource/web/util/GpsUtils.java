package org.traccar.api.resource.web.util;

import java.util.Date;

public class GpsUtils {

    public static String calculateLastGpsDetection(Date deviceTime) {
        // Obtener el tiempo actual en milisegundos
        long currentTime = System.currentTimeMillis();
        // Convertir el tiempo del dispositivo a milisegundos
        long deviceTimeMillis = deviceTime.getTime();
        // Calcular la diferencia de tiempo en segundos
        long differenceInSeconds = (currentTime - deviceTimeMillis) / 1000;

        // Si la diferencia es menor a un minuto, devolver el tiempo en segundos
        if (differenceInSeconds < 60) {
            return differenceInSeconds + " seconds ago";
        }
        // Si la diferencia es mayor a un minuto, devolver el tiempo en minutos
        long differenceInMinutes = differenceInSeconds / 60;
        return differenceInMinutes + " minutes ago";
    }
}