package uz.inha.chads.ui.feature_cards.screen_card_details

import uz.inha.chads.domain.core.OperationResult
import uz.inha.chads.ui.core.resources.UiText
import uz.inha.chads.ui.feature_cards.model.CardUi
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class CardDetailsState(
    val card: CardUi? = null,
    val showCardSkeleton: Boolean = true,
    val error: UiText? = null,
    val showLoading: Boolean = false,
    val showDeleteCardDialog: Boolean = false,
    val cardDeletedResultEvent: StateEventWithContent<OperationResult<Unit>> = consumed(),
    val setCardAsPrimaryEvent: StateEventWithContent<OperationResult<Unit>> = consumed()
)
