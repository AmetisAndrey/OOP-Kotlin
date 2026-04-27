package orders

enum class OrderStatus {
    PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED;

    fun canTransitionTo(newStatus: OrderStatus): Boolean {
        return when (this) {
            PENDING -> newStatus in listOf(PROCESSING, CANCELLED)
            PROCESSING -> newStatus in listOf(SHIPPED, CANCELLED)
            SHIPPED -> newStatus in listOf(DELIVERED, CANCELLED)
            DELIVERED -> false
            CANCELLED -> false
        }
    }
}