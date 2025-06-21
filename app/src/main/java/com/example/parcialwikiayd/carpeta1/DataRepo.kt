package com.example.parcialwikiayd.carpeta1

import C.ApiY
import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.example.parcialwikiayd.carpeta3.Pais
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataRepo(private val context:Context) {
    val traduccionPais = mapOf(
    "Argentina" to "argentina",
    "Brasil" to "brazil",
    "Chile" to "chile",
    "Perú" to "peru",
    "Colombia" to "colombia",
    "Uruguay" to "uruguay",
    "Paraguay" to "paraguay",
    "México" to "mexico",
    "España" to "spain",
    "Italia" to "italy",
    "Francia" to "france"
    )
    // Obtiene la  bandera por pais y una breve reseña de ese pais
    val sharedPreferences = context.getSharedPreferences("MY_SHARED_COUNTRY", Context.MODE_PRIVATE)

    fun fetchBandera(pais: String): Pais {
        val savedValue = sharedPreferences.getString(pais, null)
        val gson = Gson()

        if(savedValue != null) {
            Log.e("RESULT", "Valor desde shared Preferences")
            return try {
              gson.fromJson(savedValue,object : TypeToken<Pais>(){}.type)
           } catch (e: Exception) {
                Log.e("ERROR", "Falló la conversión desde cache", e)
                return Pais("NA","NA","NA","NA")
           }
        } else {
            Log.e("RESULT", "Value from service")
            //llama a la Api con el pais y obtiene la informacion sobre ese pais
            val result = traduccionPais[pais]?.let { ApiY().get(it) }
            //para guardar en la cache lo convierte en un string Json
            sharedPreferences.edit { putString(pais,gson.toJson(result)) }
            return result!!
        }
    }
}