package uz.inha.chads.ui.feature_app_lock.setup_applock.biometrics

import androidx.lifecycle.ViewModel
import uz.inha.chads.R
import uz.inha.chads.domain.features.app_lock.CheckIfBiometricsAvailableUseCase
import uz.inha.chads.domain.features.app_lock.SetupAppLockedWithBiometricsUseCase
import uz.inha.chads.domain.features.app_lock.model.BiometricsAvailability
import uz.inha.chads.ui.core.resources.UiText
import uz.inha.chads.ui.feature_app_lock.core.BiometricsViewModel
import uz.inha.chads.ui.feature_app_lock.core.biometrics.BiometricAuthResult
import uz.inha.chads.ui.feature_app_lock.core.biometrics.BiometricsIntent
import uz.inha.chads.ui.feature_app_lock.core.biometrics.BiometricsPromptUi
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EnableBiometricsViewModel(
    private val setupAppLockedWithBiometricsUseCase: SetupAppLockedWithBiometricsUseCase,
    private val checkIfBiometricsAvailableUseCase: CheckIfBiometricsAvailableUseCase
) : ViewModel(), BiometricsViewModel {
    private val _state = MutableStateFlow(
        EnableBiometricsState(
            prompt = BiometricsPromptUi(
                title = UiText.StringResource(R.string.setup_biometrics),
                cancelBtnText = UiText.StringResource(R.string.cancel)
            ),
            biometricsAvailability = BiometricsAvailability.Checking,
        )
    )

    val state = _state.asStateFlow()

    override fun emitBiometricsIntent(intent: BiometricsIntent) {
        when (intent) {
            is BiometricsIntent.RefreshBiometricsAvailability -> {
                val availability = checkIfBiometricsAvailableUseCase.execute()
                _state.update {
                    it.copy(biometricsAvailability = availability)
                }
            }

            is BiometricsIntent.ConsumeAuthResult -> {
                if (intent.result is BiometricAuthResult.Success) {
                    setupAppLockedWithBiometricsUseCase.execute()
                }

                _state.update {
                    it.copy(authResultEvent = triggered(intent.result))
                }
            }
        }
    }

    fun consumeAuthEvent() {
        _state.update {
            it.copy(authResultEvent = consumed())
        }
    }
}