package uz.inha.chads.domain.features.transactions.model

import uz.inha.chads.domain.features.account.model.MoneyAmount

data class TransactionRowPayload(
    val type: TransactionType,
    val amount: MoneyAmount,
    val cardId: String,
    val contactId: Long? = null,
)
