package uz.inha.chads.ui.feature_contacts.scanned_contact

sealed class ScannedContactIntent {
    data class LoadContact(val qrCode: String): ScannedContactIntent()

    object AddContact: ScannedContactIntent()
}
