package com.carwashpremiere.carwashpremieremobile;

import java.util.HashMap;
import java.util.Map;

public class Function_ActivityMap {
    public static Map<String, Class<?>> activityMap = new HashMap<>();

    static {
        activityMap.put("ServiciosAutos", Act_ServicesCars.class);
        activityMap.put("ServiciosObjetos", Act_ServicesObjects.class);
        activityMap.put("ServiciosPartes", Act_description_parts.class);
        activityMap.put("Carrito", Act_ShoppingCart.class);
        activityMap.put("MenuPrincipal", Menu.class);
        activityMap.put("ParametrosAutos", Act_CarParameters.class);
        activityMap.put("LavadoObjetos", Act_ObjectParameters.class);
        activityMap.put("DetalleOrden", Act_OrderDetail.class);
        activityMap.put("LavadoAutos", Act_DescriptionCars.class);
        activityMap.put("LavadoCompleto", Act_DescriptionCars.class);
    }

    public static Class<?> getActivityClass(String activityId) {
        return activityMap.get(activityId);
    }
}

