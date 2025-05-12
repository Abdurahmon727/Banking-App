package uz.inha.chads.ui.feature_app_lock.lock_screen

import uz.inha.chads.R
import uz.inha.chads.ui.core.resources.UiText
import uz.inha.chads.ui.feature_app_lock.components.AppLockUiState
import uz.inha.chads.ui.feature_app_lock.core.biometrics.BiometricAuthResult
import uz.inha.chads.ui.feature_app_lock.core.biometrics.BiometricsPromptUi
import uz.inha.chads.ui.feature_logout.LogoutState
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class LockScreenState(
    val uiState: AppLockUiState = AppLockUiState(),
    val biometricsPromptState: BiometricsPromptUi = BiometricsPromptUi(
        title = UiText.StringResource(R.string.unlock_app_biometrics),
        cancelBtnText = UiText.StringResource(R.string.cancel)
    ),

    val showBiometricsPromptEvent: StateEvent = consumed,
    val unlockWithPinEvent: StateEvent = consumed,
    val unlockWithBiometricsResultEvent: StateEventWithContent<BiometricAuthResult> = consumed(),

    val logoutState: LogoutState = LogoutState(),
)
