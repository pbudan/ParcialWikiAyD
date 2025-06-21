package C

import com.example.parcialwikiayd.carpeta3.Pais
import com.google.gson.Gson
import com.google.gson.JsonArray
import java.net.URL

class ApiY {
    fun get(pais: String): Pais {
        val url = "https://restcountries.com/v3.1/name/${pais}"
        val text = URL(url).readText()
        print(text)
        val jsonArray = Gson().fromJson(text, JsonArray::class.java)
        val item = jsonArray[0].asJsonObject
        val nombreComun = item["name"]?.asJsonObject?.get("common")?.asString ?: "Desconocido"
        val nombreOficial = item["name"]?.asJsonObject?.get("official")?.asString ?: "Desconocido"
        val bandera = item["flags"]?.asJsonObject?.get("png")?.asString ?: ""
        val descripcionBandera = item["flags"]?.asJsonObject?.get("alt")?.asString ?: "Sin descripción"

        return Pais(
            nombreComun = nombreComun,
            nombreOficial = nombreOficial,
            bandera = bandera,
            descripcionBandera = descripcionBandera
        )
    }
}