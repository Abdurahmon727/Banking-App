package uz.inha.chads.data.helpers

import uz.inha.chads.domain.features.account.model.MoneyAmount

inline fun <T> Iterable<T>.sumMoneyAmounts(selector: (T) -> MoneyAmount): MoneyAmount {
    var sum = MoneyAmount(0f)
    for (element in this) {
        sum += selector(element)
    }
    return sum
}