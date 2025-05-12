package uz.inha.chads.data.db.convertors

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import uz.inha.chads.domain.features.account.model.MoneyAmount

@ProvidedTypeConverter
class MoneyAmountConvertor {
    @TypeConverter
    fun moneyAmountToDb(value: MoneyAmount?): Float? {
        return value?.value
    }

    @TypeConverter
    // FIXME currency when needed
    fun moneyAmountFromDb(value: Float?): MoneyAmount? {
        return if (value != null) {
            MoneyAmount(value)
        }
        else {
            null
        }
    }
}