package uz.inha.chads.ui.feature_contacts.scanned_contact

import uz.inha.chads.domain.core.OperationResult
import uz.inha.chads.ui.core.resources.UiText
import uz.inha.chads.ui.feature_contacts.model.ContactUi
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class ScannedContactScreenState(
    val isContactLoading: Boolean = true,
    val isLoading: Boolean = false,
    val contact: ContactUi? = null,
    val error: UiText? = null,
    val addContactResEvent: StateEventWithContent<OperationResult<Unit>> = consumed()
)
