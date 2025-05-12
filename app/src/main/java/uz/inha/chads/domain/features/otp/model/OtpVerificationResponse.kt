package uz.inha.chads.domain.features.otp.model

data class OtpVerificationResponse(
    val isSuccess: Boolean,
    val remainingAttempts: Int?
)
