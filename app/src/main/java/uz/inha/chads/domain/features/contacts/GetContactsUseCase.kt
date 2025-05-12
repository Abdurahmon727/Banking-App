package uz.inha.chads.domain.features.contacts

class GetContactsUseCase(
    private val contactsRepository: ContactsRepository
) {
    suspend fun execute(): List<Contact> {
        return contactsRepository.getContacts()
    }
}