package uz.inha.chads.ui.feature_account

import uz.inha.chads.domain.core.ErrorType
import uz.inha.chads.ui.feature_contacts.model.ContactUi
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class ContactPickerState(
    val isLoading: Boolean = false,
    val showContactPicker: Boolean = false,
    val selectedContact: ContactUi? = null,
    val contactSelectedErrorEvent: StateEventWithContent<ErrorType> = consumed()
)
