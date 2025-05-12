package uz.inha.chads.ui.feature_transactions

import uz.inha.chads.domain.features.transactions.model.TransactionType

sealed class TransactionHistoryIntent {
    object InitLoad: TransactionHistoryIntent()
    data class ChangeTransactionFilter(val filterByType: TransactionType?): TransactionHistoryIntent()
}
