package uz.inha.chads.ui.feature_qr_codes

import uz.inha.chads.domain.features.qr_codes.model.QrPurpose

sealed class DisplayQrIntent {
    data class GenerateQr(val qrPurpose: QrPurpose): DisplayQrIntent()
}
