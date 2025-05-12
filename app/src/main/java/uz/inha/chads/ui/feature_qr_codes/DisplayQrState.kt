package uz.inha.chads.ui.feature_qr_codes

import uz.inha.chads.ui.core.resources.UiText

data class DisplayQrState(
    val isLoading: Boolean = false,
    val qrString: String? = null,
    val error: UiText? = null,
)
