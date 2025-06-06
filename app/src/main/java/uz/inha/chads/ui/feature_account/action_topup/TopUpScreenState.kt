package uz.inha.chads.ui.feature_account.action_topup

import uz.inha.chads.domain.features.account.model.MoneyAmount
import uz.inha.chads.ui.core.resources.UiText
import uz.inha.chads.ui.feature_account.AmountPickersState
import uz.inha.chads.ui.feature_account.CardPickerState
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed

data class TopUpScreenState(
    val cardPickerState: CardPickerState = CardPickerState(),
    val amountState: AmountPickersState = AmountPickersState(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val topUpSubmittedEvent: StateEvent = consumed,
    val showSuccessDialog: Boolean = false,
    val requiredBackNavEvent: StateEvent = consumed
) {
    val proceedButtonEnabled
        get(): Boolean {
            return amountState.selectedAmount != MoneyAmount(0f) && cardPickerState.selectedCard != null
        }
}
