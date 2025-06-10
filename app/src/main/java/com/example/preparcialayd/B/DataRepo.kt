package B

import C.ApiX
import C.ApiY
import Config
import android.content.Context

class DataRepo(private val context:Context) {
    private val observers = mutableListOf<PriceObserver>()

    fun addObserver(o: PriceObserver) = observers.add(o)
    fun removeObserver(o: PriceObserver) = observers.remove(o)

    fun fetchPrice(symbol: String) {
        val price = if (Config.useX) ApiX(context).get(symbol) else ApiY().get(symbol)
        observers.forEach { it.onPrice(symbol, price) }
    }
}