package uz.inha.chads.ui.feature_home.model

import uz.inha.chads.ui.core.resources.UiText
import uz.inha.chads.ui.feature_account.MoneyAmountUi
import uz.inha.chads.ui.feature_cards.model.CardUi
import uz.inha.chads.ui.feature_profile.model.ProfileUi
import uz.inha.chads.ui.feature_savings.model.SavingUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

sealed class HomeState {
    // Single loading for all parts of screen for simplicity
    object Loading: HomeState()

    data class Success(
        val profile: ProfileUi,
        val cards: List<CardUi> = emptyList(),
        val savings: List<SavingUi> = emptyList(),
        val balance: Flow<MoneyAmountUi?> = flowOf(null),
    ): HomeState()

    data class Error(val error: UiText): HomeState()
}
