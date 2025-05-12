package uz.inha.chads.domain.features.account.account_topup

import uz.inha.chads.domain.features.account.model.MoneyAmount
import uz.inha.chads.domain.features.transactions.TransactionRepository
import uz.inha.chads.domain.features.transactions.model.TransactionRowPayload
import uz.inha.chads.domain.features.transactions.model.TransactionType

class TopUpAccountUseCase(
    private val transactionRepository: TransactionRepository,
) {
    suspend fun execute(cardId: String, amount: MoneyAmount) {
        return transactionRepository.submitTransaction(
            TransactionRowPayload(
                type = TransactionType.TOP_UP,
                amount = amount,
                contactId = null,
                cardId = cardId
            )
        )
    }
}