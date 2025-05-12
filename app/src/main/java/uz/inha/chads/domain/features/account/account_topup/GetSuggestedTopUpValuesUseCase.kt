package uz.inha.chads.domain.features.account.account_topup

import uz.inha.chads.domain.features.account.model.MoneyAmount

class GetSuggestedTopUpValuesUseCase {
    // Use unordered collection with unique values
    fun execute(): Set<MoneyAmount> {
        // Here may be additional logic, like include recent sent values
        val initialItems = setOf(
            MoneyAmount(0.25f),
            MoneyAmount(1f),
        )

        val rangeItems = (50..550 step 100).map { MoneyAmount(it.toFloat()) }

        val additionalItems = setOf(MoneyAmount(1000f))

        return (initialItems + rangeItems + additionalItems)
    }
}