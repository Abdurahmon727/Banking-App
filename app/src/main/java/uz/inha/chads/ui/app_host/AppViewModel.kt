package uz.inha.chads.ui.app_host

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.inha.chads.domain.core.ErrorType
import uz.inha.chads.domain.core.OperationResult
import uz.inha.chads.domain.features.app_lock.CheckAppLockUseCase
import uz.inha.chads.domain.features.login.CheckIfLoggedInUseCase
import uz.inha.chads.domain.features.onboarding.CheckIfPassedOnboardingUseCase
import uz.inha.chads.ui.app_host.navigation.model.ConditionalNavigation
import uz.inha.chads.ui.core.error.asUiTextError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    private val checkIfLoggedInUseCase: CheckIfLoggedInUseCase,
    private val checkIfPassedOnboardingUseCase: CheckIfPassedOnboardingUseCase,
    private val checkAppLockUseCase: CheckAppLockUseCase
) : ViewModel() {

    private val _appState: MutableStateFlow<AppState> = MutableStateFlow(AppState.Loading)
    val appState = _appState.asStateFlow()

    // This is a global app's viewModel
    init {
        emitIntent(AppIntent.EnterApp)
    }

    fun emitIntent(intent: AppIntent) {
        when (intent) {
            AppIntent.EnterApp -> {
                reduceAppLoading()
                reduceAppReadyCheck()
            }

            AppIntent.TryPostUnlock -> {
                val currState = _appState.value

                if (currState is AppState.Ready) {
                    _appState.update {
                        currState.copy(
                            requireUnlock = false
                        )
                    }
                }
            }

            is AppIntent.AppLockLogout -> {
                reduceAppReady(
                    appLocked = false,
                    conditionalNavigation = ConditionalNavigation(
                        requireLogin = true,
                        requireOnboarding = false,
                        requireCreateAppLock = false
                    )
                )
            }
        }
    }

    private fun reduceAppLoading() {
        _appState.update {
            AppState.Loading
        }
    }

    private fun reduceAppReadyCheck() {
        viewModelScope.launch {

            val isLoggedIn = OperationResult.runWrapped {
                checkIfLoggedInUseCase.execute()
            }

            when (isLoggedIn) {
                is OperationResult.Success -> {

                    val hasPassedOnboarding = checkIfPassedOnboardingUseCase.execute()
                    val appLocked = checkAppLockUseCase.execute()

                    reduceAppReady(
                        conditionalNavigation = ConditionalNavigation(
                            // Require create applock if closed this step on registration
                            requireCreateAppLock = !appLocked && isLoggedIn.data,
                            requireLogin = !isLoggedIn.data,
                            requireOnboarding = !hasPassedOnboarding
                        ),
                        appLocked = appLocked
                    )
                }

                is OperationResult.Failure -> {
                    reduceError(isLoggedIn.error.errorType)
                }
            }
        }
    }

    private fun reduceAppReady(
        conditionalNavigation: ConditionalNavigation,
        appLocked: Boolean
    ) {
        _appState.value = AppState.Ready(
            conditionalNavigation = conditionalNavigation,
            requireUnlock = appLocked
        )
    }

    private fun reduceError(errorType: ErrorType) {
        _appState.value = AppState.InitFailure(errorType.asUiTextError())
    }
}