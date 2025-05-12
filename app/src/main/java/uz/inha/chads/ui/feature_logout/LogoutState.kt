package uz.inha.chads.ui.feature_logout

import uz.inha.chads.domain.core.OperationResult
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class LogoutState(
    val isLoading: Boolean = false,
    val showLogoutDialog: Boolean = false,
    val logoutEvent: StateEventWithContent<OperationResult<Unit>> = consumed(),
)