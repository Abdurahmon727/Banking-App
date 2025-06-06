package uz.inha.chads.ui.feature_profile

sealed class ProfileScreenIntent {
    object EnterScreen: ProfileScreenIntent()
    data class ToggleMyQrDialog(val isShown: Boolean): ProfileScreenIntent()
    data class ToggleScanQrDialog(val isShown: Boolean): ProfileScreenIntent()
    data class TogglePermissionDialog(val isShown: Boolean): ProfileScreenIntent()
}
