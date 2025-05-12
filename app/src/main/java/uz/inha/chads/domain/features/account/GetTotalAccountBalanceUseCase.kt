package uz.inha.chads.domain.features.account

import uz.inha.chads.domain.features.account.model.MoneyAmount
import kotlinx.coroutines.flow.Flow

class GetTotalAccountBalanceUseCase(
    private val accountRepository: AccountRepository
) {
    fun execute(): Flow<MoneyAmount> {
        return accountRepository.getBalanceFlow()
    }
}