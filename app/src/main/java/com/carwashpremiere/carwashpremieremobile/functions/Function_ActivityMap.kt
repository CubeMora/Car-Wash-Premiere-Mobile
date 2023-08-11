package com.carwashpremiere.carwashpremieremobile.functions

import com.carwashpremiere.carwashpremieremobile.activities.Act_CarParameters
import com.carwashpremiere.carwashpremieremobile.activities.Act_DescriptionCars
import com.carwashpremiere.carwashpremieremobile.activities.Act_ObjectParameters
import com.carwashpremiere.carwashpremieremobile.activities.Act_OrderDetail
import com.carwashpremiere.carwashpremieremobile.activities.Act_ServicesCars
import com.carwashpremiere.carwashpremieremobile.activities.Act_ServicesObjects
import com.carwashpremiere.carwashpremieremobile.old.Act_ShoppingCart
import com.carwashpremiere.carwashpremieremobile.activities.Act_description_parts
import com.carwashpremiere.carwashpremieremobile.activities.Menu

object Function_ActivityMap {
    var activityMap: MutableMap<String, Class<*>> = HashMap()

    init {
        activityMap["ServiciosAutos"] = Act_ServicesCars::class.java
        activityMap["ServiciosObjetos"] = Act_ServicesObjects::class.java
        activityMap["ServiciosPartes"] = Act_description_parts::class.java
        activityMap["Carrito"] = Act_ShoppingCart::class.java
        activityMap["MenuPrincipal"] =
            Menu::class.java
        activityMap["ParametrosAutos"] = Act_CarParameters::class.java
        activityMap["LavadoObjetos"] = Act_ObjectParameters::class.java
        activityMap["DetalleOrden"] = Act_OrderDetail::class.java
        activityMap["LavadoAutos"] = Act_DescriptionCars::class.java
        activityMap["LavadoCompleto"] = Act_DescriptionCars::class.java
    }

    @JvmStatic
    fun getActivityClass(activityId: String): Class<*> {
        return activityMap[activityId]!!
    }
}