package uz.inha.chads.ui.feature_account.components.account_actions

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import uz.inha.chads.R

sealed class AccountAction(
    @DrawableRes val icon: Int,
    @StringRes val uiTitle: Int,
) {
    object SendMoney : AccountAction(R.drawable.ic_send, R.string.send)
    object RequestMoney : AccountAction(R.drawable.ic_request, R.string.request)
    object TopUp : AccountAction(R.drawable.ic_topup, R.string.top_up)
    object Pay : AccountAction(R.drawable.ic_pay, R.string.pay)
}
