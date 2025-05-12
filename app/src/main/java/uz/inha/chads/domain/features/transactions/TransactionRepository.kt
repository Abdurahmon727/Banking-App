package uz.inha.chads.domain.features.transactions

import androidx.paging.PagingData
import uz.inha.chads.domain.features.transactions.model.Transaction
import uz.inha.chads.domain.features.transactions.model.TransactionRowPayload
import uz.inha.chads.domain.features.transactions.model.TransactionStatus
import uz.inha.chads.domain.features.transactions.model.TransactionType
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun getTransactions(filterByType: TransactionType?): Flow<PagingData<Transaction>>
    fun getTransactionStatusFlow(transactionId: Long): Flow<TransactionStatus>
    suspend fun submitTransaction(payload: TransactionRowPayload)
}