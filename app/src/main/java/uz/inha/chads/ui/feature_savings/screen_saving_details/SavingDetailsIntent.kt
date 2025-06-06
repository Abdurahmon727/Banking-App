package uz.inha.chads.ui.feature_savings.screen_saving_details

sealed class SavingDetailsIntent {
    data class EnterScreen(val savingId: Long): SavingDetailsIntent()
    data class LoadLinkedCard(val cardId: String): SavingDetailsIntent()
}
