package uz.inha.chads.domain.features.signup

import uz.inha.chads.domain.features.otp.model.OtpConfiguration
import uz.inha.chads.domain.features.otp.model.OtpVerificationResponse

interface SignUpRepository {
    suspend fun signUpWithEmail(payload: SignUpPayload)

    suspend fun confirmSignUpWithEmail(
        otpCode: String,
        otpConfiguration: OtpConfiguration
    ): OtpVerificationResponse
}