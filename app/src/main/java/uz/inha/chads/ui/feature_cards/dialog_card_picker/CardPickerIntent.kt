package uz.inha.chads.ui.feature_cards.dialog_card_picker

sealed class CardPickerIntent {
    data class LoadCards(
        val defaultSelectedCardId: String?
    ): CardPickerIntent()
}