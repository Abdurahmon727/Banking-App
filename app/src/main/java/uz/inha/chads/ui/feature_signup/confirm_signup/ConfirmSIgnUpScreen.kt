package uz.inha.chads.ui.feature_signup.confirm_signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uz.inha.chads.domain.core.OperationResult
import uz.inha.chads.domain.features.otp.model.OtpOperationType
import uz.inha.chads.domain.features.otp.model.OtpType
import uz.inha.chads.ui.app_host.host_utils.LocalScopedSnackbarState
import uz.inha.chads.ui.components.FullscreenProgressBar
import uz.inha.chads.ui.components.error.ErrorFullScreen
import uz.inha.chads.ui.components.snackbar.SnackBarMode
import uz.inha.chads.ui.core.effects.EnterScreenEffect
import uz.inha.chads.ui.core.error.asUiTextError
import uz.inha.chads.ui.feature_otp.OtpConfirmationIntent
import uz.inha.chads.ui.feature_otp.OtpConfirmationScreen
import de.palm.composestateevents.EventEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun ConfirmSignUpScreen(
    email: String,
    onCodeVerified: () -> Unit,
    vIewModel: ConfirmEmailSignUpVIewModel = koinViewModel(),
) {
    val state = vIewModel.otpState.collectAsStateWithLifecycle().value
    val snackBarState = LocalScopedSnackbarState.current
    val context = LocalContext.current

    when {
        state.isInitialLoading -> FullscreenProgressBar()

        state.otpState != null && state.screenError == null -> {
            OtpConfirmationScreen(
                state = state.otpState,
                onIntent = { vIewModel.emitOtpIntent(it) }
            )

            EventEffect(
                event = state.otpState.codeSubmittedEvent,
                onConsumed = vIewModel::consumeOtpSubmittedEvent
            ) { codeVerifyResult ->
                when (codeVerifyResult) {
                    is OperationResult.Failure -> {
                        snackBarState.show(
                            message = codeVerifyResult.error.errorType.asUiTextError().asString(context),
                        )
                    }

                    is OperationResult.Success -> {
                        if (codeVerifyResult.data.isSuccess) {
                            onCodeVerified()
                        } else {
                            snackBarState.show(
                                message = "Wrong OTP code.\nRemaining attempts: ${codeVerifyResult.data.remainingAttempts}",
                                snackBarMode = SnackBarMode.Negative
                            )
                        }
                    }
                }
            }

            EventEffect(
                event = state.otpState.codeResentEvent,
                onConsumed = vIewModel::consumeOtpResentEvent
            ) { otpGenerationRes ->
                when (otpGenerationRes) {
                    is OperationResult.Failure -> {
                        snackBarState.show(
                            message = otpGenerationRes.error.errorType.asUiTextError().asString(context),
                        )
                    }

                    is OperationResult.Success -> {
                        snackBarState.show(
                            message = "Code has been sent.\nRemaining attempts: ${otpGenerationRes.data.remainingAttempts}",
                            snackBarMode = SnackBarMode.Neutral
                        )
                    }
                }
            }
        }

        state.screenError != null -> ErrorFullScreen(error = state.screenError)
    }

    EnterScreenEffect() {
        vIewModel.emitOtpIntent(
            OtpConfirmationIntent.RequestInitialOtp(
                otpType = OtpType.EMAIL,
                otpOperationType = OtpOperationType.SIGN_UP_WITH_EMAIL,
                otpDestination = email
            )
        )
    }
}
