package uz.inha.chads.domain.features.otp

import uz.inha.chads.domain.features.otp.model.OtpConfiguration
import uz.inha.chads.domain.features.otp.model.OtpGenerationResponse

class RequestOtpGenerationUseCase(
    private val otpRepository: OtpRepository
) {
    suspend fun execute(otpConfiguration: OtpConfiguration): OtpGenerationResponse {
        return otpRepository.requestGenerateOtpCode(otpConfiguration)
    }
}