package uz.inha.chads.ui.app_host

import uz.inha.chads.ui.app_host.navigation.model.ConditionalNavigation
import uz.inha.chads.ui.core.resources.UiText

// Global app state, can include auth check result, app lock flag and so on
// consider researching better approach
sealed class AppState {
    object Loading: AppState()

    data class Ready(
        val conditionalNavigation: ConditionalNavigation,
        val requireUnlock: Boolean = false
    ): AppState()

    data class InitFailure(
        val error: UiText
    ): AppState()
}
