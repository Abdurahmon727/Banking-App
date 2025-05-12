package uz.inha.chads.ui.feature_profile

import uz.inha.chads.ui.core.resources.UiText
import uz.inha.chads.ui.feature_logout.LogoutState
import uz.inha.chads.ui.feature_profile.model.ProfileUi

data class ProfileScreenState(
    val profile: ProfileUi? = null,
    val isProfileLoading: Boolean = true,
    val error: UiText? = null,
    val logoutState: LogoutState = LogoutState(),
    val showMyQrDialog: Boolean = false,
    val showScanQrDialog: Boolean = false,
    val showPermissionDialog: Boolean = false
)

