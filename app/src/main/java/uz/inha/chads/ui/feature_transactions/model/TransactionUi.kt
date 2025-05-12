package uz.inha.chads.ui.feature_transactions.model

import uz.inha.chads.domain.features.transactions.model.Transaction
import uz.inha.chads.domain.features.transactions.model.TransactionStatus
import uz.inha.chads.domain.features.transactions.model.TransactionType
import uz.inha.chads.ui.core.extensions.getFormattedDate
import uz.inha.chads.ui.feature_account.MoneyAmountUi
import uz.inha.chads.ui.feature_contacts.model.ContactUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class TransactionUi(
    val id: Long,
    val type: TransactionType,
    val recentStatus: TransactionStatus,
    val statusFlow: Flow<TransactionStatus>,
    val value: MoneyAmountUi,
    val transactionDate: String,
    val contact: ContactUi?
) {
    companion object {
        fun mock(
            id: Long = 0,
            type: TransactionType = TransactionType.SEND,
            status: TransactionStatus = TransactionStatus.COMPLETED,
            transactionDate: String = "13 Oct 2021",
            value: MoneyAmountUi = MoneyAmountUi("$200.50"),
            contact: ContactUi? = ContactUi.mock()
        ): TransactionUi {
            return TransactionUi(
                id = id,
                type = type,
                transactionDate = transactionDate,
                recentStatus = status,
                statusFlow = flowOf(status),
                value = value,
                contact = contact,
            )
        }

        fun mapFromDomain(
            transaction: Transaction,
            statusFlow: Flow<TransactionStatus>? = null
        ): TransactionUi {
            val contact = if (transaction.linkedContact != null) {
                ContactUi.mapFromDomain(transaction.linkedContact)
            } else {
                null
            }

            return TransactionUi(
                id = transaction.id,
                value = MoneyAmountUi.mapFromDomain(transaction.value),
                transactionDate = transaction.createdDate.getFormattedDate("dd MMM yyyy HH:mm"),
                contact = contact,
                type = transaction.type,
                recentStatus = transaction.recentStatus,
                statusFlow = statusFlow ?: flowOf(transaction.recentStatus)
            )
        }
    }
}
