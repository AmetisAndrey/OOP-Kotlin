package orders

import users.Customer
import users.User
import users.Admin
import results.OperationResult
import models.OrderItem

class Order(
    val id: Int,
    val customer: Customer,
    val items: List<OrderItem>
) {
    var status: OrderStatus = OrderStatus.PENDING
        private set

    fun cancel(actor: User): OperationResult {
        return if (actor is Customer && actor.id != customer.id) {
            OperationResult.Failure("Only the order owner or admin can cancel the order")
        } else if (status.canTransitionTo(OrderStatus.CANCELLED)) {
            status = OrderStatus.CANCELLED
            OperationResult.Success("Order $id cancelled")
        } else {
            OperationResult.Failure("Cannot cancel order in $status state")
        }
    }

    fun updateStatus(newStatus: OrderStatus, actor: User): OperationResult {
        return if (actor !is Admin) {
            OperationResult.Failure("Only admin can change order status")
        } else if (status.canTransitionTo(newStatus)) {
            status = newStatus
            OperationResult.Success("Order $id status updated to $newStatus")
        } else {
            OperationResult.Failure("Invalid state transition from $status to $newStatus")
        }
    }

    fun calculateTotal(discountCalculator: DiscountCalculator): Double {
        val subtotal = items.sumOf { it.product.price * it.quantity }
        val discount = discountCalculator.calculateDiscount(subtotal)
        return subtotal - discount
    }

    override fun toString(): String = "Order #$id for ${customer.name}, status: $status, items: ${items.size}"
}