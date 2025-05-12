package uz.inha.chads.domain.features.transactions.model

import uz.inha.chads.domain.features.account.model.MoneyAmount
import uz.inha.chads.domain.features.contacts.Contact

data class Transaction(
    val id: Long,
    val type: TransactionType,
    val value: MoneyAmount,
    val linkedContact: Contact?,
    val recentStatus: TransactionStatus,
    val createdDate: Long,
    val updatedStatusDate: Long,
)
