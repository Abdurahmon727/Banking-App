package uz.inha.chads.ui.feature_contacts.model

import uz.inha.chads.domain.features.contacts.Contact
import uz.inha.chads.ui.core.extensions.splitStringWithDivider

data class ContactUi(
    val name: String,
    val id: Long,
    val cardNumber: String,
    val profilePictureUrl: String
) {
    companion object {
        fun mock() = ContactUi(
            name = "Vina Andini",
            id = 0,
            cardNumber = "0000111122223333".splitStringWithDivider(),
            profilePictureUrl = ""
        )

        fun mapFromDomain(contact: Contact) = ContactUi(
            name = contact.name,
            id = contact.id,
            cardNumber = contact.linkedCardNumber.splitStringWithDivider(),
            profilePictureUrl = contact.profilePic
        )
    }
}
