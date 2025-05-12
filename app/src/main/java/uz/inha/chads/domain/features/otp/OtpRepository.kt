package uz.inha.chads.domain.features.otp

import uz.inha.chads.domain.features.otp.model.OtpConfiguration
import uz.inha.chads.domain.features.otp.model.OtpGenerationResponse
import uz.inha.chads.domain.features.otp.model.OtpVerificationResponse

interface OtpRepository {
    suspend fun requestGenerateOtpCode(otpConfiguration: OtpConfiguration): OtpGenerationResponse

    suspend fun verifyOtpCode(
        otpConfiguration: OtpConfiguration,
        code: String,
    ): OtpVerificationResponse
}