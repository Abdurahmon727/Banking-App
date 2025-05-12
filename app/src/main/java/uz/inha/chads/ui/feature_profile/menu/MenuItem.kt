package uz.inha.chads.ui.feature_profile.menu

import uz.inha.chads.ui.core.resources.UiText

sealed class MenuItem {
    data class Item(
        val entry: MenuEntry,
        val onClick: (MenuEntry) -> Unit = {}
    ): MenuItem()

    data class Section(
        val title: UiText
    ): MenuItem()
}