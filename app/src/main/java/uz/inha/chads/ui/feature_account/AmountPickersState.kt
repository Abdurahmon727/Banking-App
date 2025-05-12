package uz.inha.chads.ui.feature_account

import uz.inha.chads.domain.features.account.model.MoneyAmount
import uz.inha.chads.ui.core.resources.UiText

data class AmountPickersState(
    val selectedAmount: MoneyAmount = MoneyAmount(0F),
    val maxAmount: MoneyAmount? = null,
    val proposedValues: Set<MoneyAmount> = emptySet(),
    val pickersEnabled: Boolean = true,
    val error: UiText? = null
)
