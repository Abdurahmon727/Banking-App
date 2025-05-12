package uz.inha.chads.ui.feature_account

import uz.inha.chads.domain.core.ErrorType
import uz.inha.chads.ui.feature_cards.model.CardUi
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class CardPickerState(
    val isLoading: Boolean = false,
    val showCardPicker: Boolean = false,
    val selectedCard: CardUi? = null,
    val cardSelectErrorEvent: StateEventWithContent<ErrorType> = consumed()
)