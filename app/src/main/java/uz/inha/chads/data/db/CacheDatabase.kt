package uz.inha.chads.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.inha.chads.data.cards.cache.CardEntity
import uz.inha.chads.data.cards.cache.CardsDao
import uz.inha.chads.data.db.convertors.MoneyAmountConvertor
import uz.inha.chads.data.transactions.db.TransactionDao
import uz.inha.chads.data.transactions.db.TransactionEntity

@Database(entities = [CardEntity::class, TransactionEntity::class], version = 1)
@TypeConverters(MoneyAmountConvertor::class)
abstract class CacheDatabase: RoomDatabase() {
    abstract fun getCardsDao(): CardsDao
    abstract fun getTransactionsDao(): TransactionDao
}
