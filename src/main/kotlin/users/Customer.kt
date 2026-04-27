package users

import models.Address

class Customer(
    id: Int,
    name: String,
    email: String,
    val address: Address
) : User(id, name, email) {

    override fun getRole(): String = "Customer"
}