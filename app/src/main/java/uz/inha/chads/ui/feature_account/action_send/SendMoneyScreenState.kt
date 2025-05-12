package uz.inha.chads.ui.feature_account.action_send

import uz.inha.chads.domain.features.account.model.MoneyAmount
import uz.inha.chads.ui.core.resources.UiText
import uz.inha.chads.ui.feature_account.AmountPickersState
import uz.inha.chads.ui.feature_account.CardPickerState
import uz.inha.chads.ui.feature_account.ContactPickerState
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed

data class SendMoneyScreenState(
    val cardPickerState: CardPickerState = CardPickerState(),
    val amountState: AmountPickersState = AmountPickersState(
        pickersEnabled = false,
    ),
    val contactPickerState: ContactPickerState = ContactPickerState(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val sendSubmittedEvent: StateEvent = consumed,
    val showSuccessDialog: Boolean = false,
    val requiredBackNavEvent: StateEvent = consumed
) {
    val proceedButtonEnabled
        get() = amountState.selectedAmount != MoneyAmount(0f)
                && cardPickerState.selectedCard != null
                && !cardPickerState.isLoading
                && contactPickerState.selectedContact != null
                && !contactPickerState.isLoading
}
