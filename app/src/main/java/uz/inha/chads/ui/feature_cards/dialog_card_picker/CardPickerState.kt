package uz.inha.chads.ui.feature_cards.dialog_card_picker

import uz.inha.chads.ui.core.resources.UiText
import uz.inha.chads.ui.feature_cards.model.CardUi

data class CardPickerState(
    val isLoading: Boolean = false,
    val cards: List<CardUi>? = null,
    val selectedCardId: String? = null,
    val error: UiText? = null,
)
