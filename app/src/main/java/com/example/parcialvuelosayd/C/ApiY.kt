package C

import com.example.parcialvuelosayd.A.BoundingBox
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.net.URL

class ApiY {
    fun get(pais: BoundingBox): MutableList<String> {
        val url = "https://opensky-network.org/api/states/all?lamin=${pais.latMin}&lomin=${pais.lonMin}&lamax=${pais.latMax}&lomax=${pais.lonMax}"
        val text = URL(url).readText()
        print(text)
        val jsonObject = Gson().fromJson(text, JsonObject::class.java)
        val states = jsonObject["states"].asJsonArray
        val vuelos = mutableListOf<String>()
        for (i in 0 until minOf(5,states.size())){
            val vueloArray = states[i].asJsonArray
            val callsign = vueloArray[1].asString.trim()
            val originCountry = vueloArray[2].asString
            val altitude = if (!vueloArray[7].isJsonNull) vueloArray[7].asDouble.toInt().toString() else "N/A"
            val velocity = if (!vueloArray[9].isJsonNull) vueloArray[9].asDouble.toInt().toString() else "N/A"

            val info = "Vuelo: $callsign – País: $originCountry – Altura: $altitude m – Velocidad: $velocity km/h"
            vuelos.add(info)
        }
        return vuelos
    }
}