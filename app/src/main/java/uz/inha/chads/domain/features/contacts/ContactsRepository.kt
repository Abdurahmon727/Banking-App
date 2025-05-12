package uz.inha.chads.domain.features.contacts

interface ContactsRepository {
    suspend fun getContacts(): List<Contact>

    suspend fun getContactById(contactId: Long): Contact

    suspend fun getContactFromQr(qrCode: String): Contact
}