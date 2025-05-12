package uz.inha.chads.domain.features.login

interface LoginRepository {
    suspend fun loginWithEmail(email: String, password: String)
    suspend fun checkIfLoggedIn(): Boolean
    suspend fun logOut()
}