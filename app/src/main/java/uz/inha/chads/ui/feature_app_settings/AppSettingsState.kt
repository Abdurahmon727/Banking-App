package uz.inha.chads.ui.feature_app_settings

import uz.inha.chads.R
import uz.inha.chads.domain.features.app_lock.model.BiometricsAvailability
import uz.inha.chads.ui.core.resources.UiText
import uz.inha.chads.ui.feature_app_lock.core.biometrics.BiometricAuthResult
import uz.inha.chads.ui.feature_app_lock.core.biometrics.BiometricsPromptUi
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class AppSettingsState(
    val biometricPrompt: BiometricsPromptUi = BiometricsPromptUi(
        title = UiText.StringResource(R.string.unlock_app_biometrics),
        cancelBtnText = UiText.StringResource(R.string.cancel)
    ),
    val biometricsAvailability: BiometricsAvailability,
    val isAppLockedWithBiometrics: Boolean,
    val biometricsAuthEvent: StateEventWithContent<BiometricAuthResult> = consumed()
)
