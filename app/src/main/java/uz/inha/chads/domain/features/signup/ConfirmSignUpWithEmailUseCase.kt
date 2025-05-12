package uz.inha.chads.domain.features.signup

import uz.inha.chads.domain.features.otp.model.OtpConfiguration
import uz.inha.chads.domain.features.otp.model.OtpVerificationResponse

class ConfirmSignUpWithEmailUseCase(
    private val signUpRepository: SignUpRepository
) {
    suspend fun execute(
        otpCode: String,
        otpConfiguration: OtpConfiguration
    ): OtpVerificationResponse {
        return signUpRepository.confirmSignUpWithEmail(otpCode, otpConfiguration)
    }
}