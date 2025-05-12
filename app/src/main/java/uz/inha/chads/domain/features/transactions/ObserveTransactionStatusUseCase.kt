package uz.inha.chads.domain.features.transactions

import uz.inha.chads.domain.features.transactions.model.TransactionStatus
import kotlinx.coroutines.flow.Flow

class ObserveTransactionStatusUseCase(
    private val transactionRepository: TransactionRepository
) {
    fun execute(transactionId: Long): Flow<TransactionStatus> {
        return transactionRepository.getTransactionStatusFlow(transactionId)
    }
}