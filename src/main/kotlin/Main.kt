import models.*
import users.*
import orders.*
import results.OperationResult

fun main() {
    // Products
    val laptop = Product(1, "Laptop", 1200.0, 10)
    val mouse = Product(2, "Mouse", 25.0, 50)

    // Users
    val address = Address("Lenina 1", "Moscow", "101000")
    val customer = Customer(101, "Ivan Petrov", "ivan@example.com", address)
    val admin = Admin(999, "Admin", "admin@shop.com")

    // Polymorphism demo
    println("=== Users ===")
    val users: List<User> = listOf(customer, admin)
    users.forEach { println(it) }

    // Create order
    val orderItems = listOf(
        OrderItem(laptop, 1),
        OrderItem(mouse, 2)
    )
    val order = Order(1001, customer, orderItems)
    println("\n=== Order created ===")
    println(order)

    // Order status transitions
    println("\n=== Order states ===")
    println("Current status: ${order.status}")
    println("Can transition to PROCESSING: ${order.status.canTransitionTo(OrderStatus.PROCESSING)}")
    println("Can transition to CANCELLED: ${order.status.canTransitionTo(OrderStatus.CANCELLED)}")

    // Cancel by customer
    println("\n=== Cancel order by customer ===")
    handleResult(order.cancel(customer))
    println("Status after cancel: ${order.status}")

    // Restore by admin (but cannot restore from CANCELLED)
    println("\n=== Admin tries to restore to PENDING ===")
    handleResult(order.updateStatus(OrderStatus.PENDING, admin))

    // Try to change status after cancellation (invalid)
    println("\n=== Admin tries to change to PROCESSING from CANCELLED ===")
    handleResult(order.updateStatus(OrderStatus.PROCESSING, admin))
    println("Current status: ${order.status}")

    // Create new order for discount calculation (previous order is cancelled)
    val order2 = Order(1002, customer, orderItems)
    println("\n=== New order for discount calculation ===")
    println(order2)

    // Discount strategies
    println("\n=== Total calculation with different discounts ===")
    println("No discount: ${order2.calculateTotal(NoDiscount())} rub.")
    println("10% discount: ${order2.calculateTotal(PercentageDiscount(10.0))} rub.")
    println("Fixed discount 50 rub: ${order2.calculateTotal(FixedDiscount(50.0))} rub.")

    // Show encapsulation
    // order2.status = OrderStatus.DELIVERED // Compilation error - private set
}

fun handleResult(result: OperationResult) {
    when (result) {
        is OperationResult.Success -> println("SUCCESS: ${result.message}")
        is OperationResult.Failure -> println("FAILURE: ${result.error}")
    }
}