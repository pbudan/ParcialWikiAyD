package com.example.parcialvuelosayd.C

import C.ApiY
import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.example.parcialvuelosayd.A.BoundingBox
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataRepo(private val context:Context) {
    val paisCoordenadas = mapOf(
        "Argentina" to BoundingBox(-55.0, -20.0, -75.0, -53.0),
        "Chile" to BoundingBox(-56.0, -17.0, -75.0, -66.0),
        "Brasil" to BoundingBox(-35.0, 5.0, -74.0, -35.0),
        "Perú" to BoundingBox(-18.0, 1.0, -82.0, -68.0)
    )
    // Shared Preferences es como una cache en Android. Podemos tener diferentes SharedPreferences, por eso le ponemos un nombre.
    // En este caso MY_SHARED_PREFERENCES
    // Shared preferences se limpia al desinstalar o al limpiar el storage desde las preferencias de la app
    val sharedPreferences = context.getSharedPreferences("MY_SHARED_PREFERENCES", Context.MODE_PRIVATE)

    fun fetchVuelos(pais: String): MutableList<String> {
        val savedValue = sharedPreferences.getString(pais, null)
        val gson = Gson()

        if(savedValue != null) {
            Log.e("RESULT", "Value from shared Preferences")
            return try {
              gson.fromJson(savedValue,object : TypeToken<MutableList<String>>(){}.type)
           } catch (e: Exception) {
               mutableListOf()
           }
        } else {
            Log.e("RESULT", "Value from service")
            //llama a la Api con las coordenadas del pais y obtiene todos los vuelos que pasan por sobre ese pais
            val result = paisCoordenadas[pais]?.let { ApiY().get(it) }
            //para guardar la lista de vuelos en la cache a convierte en un string Json
            sharedPreferences.edit { putString(pais,gson.toJson(result)) }
            return result!!
        }
    }
}