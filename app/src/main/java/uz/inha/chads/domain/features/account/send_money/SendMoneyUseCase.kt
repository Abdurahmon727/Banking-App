package uz.inha.chads.domain.features.account.send_money

import uz.inha.chads.domain.features.account.model.MoneyAmount
import uz.inha.chads.domain.features.transactions.TransactionRepository
import uz.inha.chads.domain.features.transactions.model.TransactionRowPayload
import uz.inha.chads.domain.features.transactions.model.TransactionType

class SendMoneyUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(
        amount: MoneyAmount,
        fromCardId: String,
        contactId: Long,
    ) {
        return transactionRepository.submitTransaction(
            TransactionRowPayload(
                type = TransactionType.SEND,
                amount = amount,
                cardId = fromCardId,
                contactId = contactId
            )
        )
    }
}