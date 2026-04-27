package users

class Admin(
    id: Int,
    name: String,
    email: String
) : User(id, name, email) {

    override fun getRole(): String = "Admin"
}