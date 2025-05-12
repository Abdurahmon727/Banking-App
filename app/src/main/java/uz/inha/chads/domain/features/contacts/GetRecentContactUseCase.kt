package uz.inha.chads.domain.features.contacts

class GetRecentContactUseCase(
    private val contactsRepository: ContactsRepository
) {
    suspend fun execute(): Contact? {
        // Here may be logic of recent contact for UI
        return contactsRepository.getContacts().random()
    }
}