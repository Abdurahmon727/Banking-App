package uz.inha.chads.domain.features.qr_codes

import uz.inha.chads.domain.features.qr_codes.model.QrPurpose
import kotlinx.coroutines.delay

class GenerateQrCodeUseCase {
    suspend fun execute(qrPurpose: QrPurpose): String {
        delay(300L)
        return when (qrPurpose) {
            // Handle in repositories later
            QrPurpose.PROFILE_CONNECTION -> "by.alexandr7035.banking:addcontact"
        }
    }
}