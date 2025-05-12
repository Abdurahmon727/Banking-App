package uz.inha.chads.domain.features.signup

class SignUpWithEmailUseCase(
    private val signUpRepository: SignUpRepository
) {
    suspend fun execute(payload: SignUpPayload) {
        return signUpRepository.signUpWithEmail(payload)
    }
}