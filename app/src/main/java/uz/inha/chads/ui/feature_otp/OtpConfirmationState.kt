package uz.inha.chads.ui.feature_otp

import uz.inha.chads.domain.core.OperationResult
import uz.inha.chads.domain.features.otp.model.OtpGenerationResponse
import uz.inha.chads.domain.features.otp.model.OtpVerificationResponse
import uz.inha.chads.ui.feature_cards.screen_add_card.UiField
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class OtpConfirmationState(
    val isLoading: Boolean = false,
    val codeLength: Int = 4,
    val code: UiField = UiField(""),
    val codeSentTo: String = "",
    val submitBtnEnabled: Boolean = false,
    val codeSubmittedEvent: StateEventWithContent<OperationResult<OtpVerificationResponse>> = consumed(),
    val codeResentEvent: StateEventWithContent<OperationResult<OtpGenerationResponse>> = consumed(),
)
