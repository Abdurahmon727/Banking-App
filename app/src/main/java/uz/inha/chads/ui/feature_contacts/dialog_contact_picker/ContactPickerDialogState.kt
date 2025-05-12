package uz.inha.chads.ui.feature_contacts.dialog_contact_picker

import uz.inha.chads.ui.core.resources.UiText
import uz.inha.chads.ui.feature_contacts.model.ContactUi

data class ContactPickerDialogState(
    val isLoading: Boolean = false,
    val contacts: List<ContactUi>? = null,
    val selectedContactId: Long? = null,
    val error: UiText? = null,
)
