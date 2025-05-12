package uz.inha.chads.data.transactions.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.inha.chads.domain.features.account.model.MoneyAmount
import uz.inha.chads.domain.features.transactions.model.TransactionStatus
import uz.inha.chads.domain.features.transactions.model.TransactionType

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: TransactionType,
    val value: MoneyAmount,
    val recentStatus: TransactionStatus,
    val cardId: String,
    val linkedContactId: Long? = null,
    val createdDate: Long,
    val updatedStatusDate: Long,
)
