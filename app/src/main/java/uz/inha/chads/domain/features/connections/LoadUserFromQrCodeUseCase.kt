package uz.inha.chads.domain.features.connections

import uz.inha.chads.domain.features.contacts.Contact
import uz.inha.chads.domain.features.contacts.ContactsRepository

class LoadUserFromQrCodeUseCase(
    private val contactsRepository: ContactsRepository
) {
    suspend fun execute(qrCode: String): Contact {
        return contactsRepository.getContactFromQr(qrCode)
    }
}