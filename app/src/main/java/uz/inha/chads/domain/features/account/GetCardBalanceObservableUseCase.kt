package uz.inha.chads.domain.features.account

import uz.inha.chads.domain.features.account.model.MoneyAmount
import kotlinx.coroutines.flow.Flow

class GetCardBalanceObservableUseCase(
    private val accountRepository: AccountRepository
) {
    suspend fun execute(cardId: String): Flow<MoneyAmount> {
        return accountRepository.getCardBalanceFlow(cardId)
    }
}