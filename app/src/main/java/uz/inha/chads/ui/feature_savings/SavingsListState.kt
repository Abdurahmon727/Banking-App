package uz.inha.chads.ui.feature_savings

import uz.inha.chads.ui.core.resources.UiText
import uz.inha.chads.ui.feature_savings.model.SavingUi

sealed class SavingsListState {
    data class Success(
        val savings: List<SavingUi>,
    ) : SavingsListState()

    data class Error(val error: UiText) : SavingsListState()

    object Loading : SavingsListState()
}
