package uz.inha.chads.ui.feature_transactions

import androidx.paging.PagingData
import uz.inha.chads.ui.core.resources.UiText
import uz.inha.chads.ui.feature_transactions.model.TransactionUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

data class TransactionHistoryState(
    val transactionsPagingState: Flow<PagingData<TransactionUi>> = MutableStateFlow(PagingData.empty()),
    val screenError: UiText? = null
)
