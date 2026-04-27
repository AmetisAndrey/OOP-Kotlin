package users

abstract class User(
    val id: Int,
    val name: String,
    val email: String
) {
    abstract fun getRole(): String

    override fun toString(): String = "$name ($email, ${getRole()})"
}