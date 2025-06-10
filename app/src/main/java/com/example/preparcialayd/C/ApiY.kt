package C

import java.net.URL

class ApiY {
    fun get(symbol: String): Double {
        val url = "https://api.alternative.me/v2/ticker/1/?convert=USD"
        val text = URL(url).readText()
        return text.split("\"price\":")[1].split("}")[0].toDoubleOrNull() ?: 0.0
    }
}