package uz.inha.chads.ui.feature_contacts.dialog_contact_picker

sealed class ContactPickerDialogIntent {
    data class LoadContacts(
        val defaultSelectedContactId: Long?
    ): ContactPickerDialogIntent()
}
