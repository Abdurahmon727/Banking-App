package uz.inha.chads.ui.feature_cards.screen_card_list

import uz.inha.chads.ui.core.resources.UiText
import uz.inha.chads.ui.feature_cards.model.CardUi

data class CardListState(
    val isLoading: Boolean = true,
    val cards: List<CardUi> = emptyList(),
    val floatingAddCardShown: Boolean = false,
    val error: UiText? = null,
)
