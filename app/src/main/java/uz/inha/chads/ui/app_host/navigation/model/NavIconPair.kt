package uz.inha.chads.ui.app_host.navigation.model

import androidx.annotation.DrawableRes

data class NavIconPair(
    @DrawableRes val unselected: Int,
    @DrawableRes val selected: Int
)
