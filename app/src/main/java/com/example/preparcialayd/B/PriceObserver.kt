package B

interface PriceObserver {
    fun onPrice(symbol: String, price: Double)
}