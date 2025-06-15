package C
import android.content.Context
import org.json.JSONObject

class ApiX(private val context:Context) {
    fun get(symbol: String): Double {
        return try {
            // Leer archivo desde assets/A/rates.json
            val inputStream = context.assets.open("A/rates.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val json = JSONObject(jsonString)
            val rates = json.getJSONObject("rates")
            rates.getDouble(symbol.uppercase())
        } catch (e: Exception) {
            e.printStackTrace()
            0.0
        }
    }
}