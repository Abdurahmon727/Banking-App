package uz.inha.chads.domain.features.signup

data class SignUpPayload(
    val fullName: String,
    val email: String,
    val password: String,
)
