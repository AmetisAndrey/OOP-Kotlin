package orders

interface DiscountCalculator {
    fun calculateDiscount(amount: Double): Double
}

class NoDiscount : DiscountCalculator {
    override fun calculateDiscount(amount: Double): Double = 0.0
}

class PercentageDiscount(val percent: Double) : DiscountCalculator {
    override fun calculateDiscount(amount: Double): Double = amount * percent / 100.0
}

class FixedDiscount(val discountAmount: Double) : DiscountCalculator {
    override fun calculateDiscount(amount: Double): Double = minOf(discountAmount, amount)
}