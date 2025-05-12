package uz.inha.chads.ui.feature_signup.confirm_signup

import uz.inha.chads.domain.features.otp.model.OtpConfiguration
import uz.inha.chads.ui.core.resources.UiText
import uz.inha.chads.ui.feature_otp.OtpConfirmationState

data class ConfirmSignUpScreenState(
    val isInitialLoading: Boolean = true,
    val otpConfiguration: OtpConfiguration? = null,
    val otpState: OtpConfirmationState? = null,
    val screenError: UiText? = null,
)