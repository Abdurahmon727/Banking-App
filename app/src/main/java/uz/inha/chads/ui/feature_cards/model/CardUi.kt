package uz.inha.chads.ui.feature_cards.model

import androidx.compose.ui.graphics.Color
import uz.inha.chads.R
import uz.inha.chads.domain.features.cards.model.CardType
import uz.inha.chads.domain.features.cards.model.PaymentCard
import uz.inha.chads.ui.core.extensions.getFormattedDate
import uz.inha.chads.ui.core.extensions.splitStringWithDivider
import uz.inha.chads.ui.core.resources.UiText
import uz.inha.chads.ui.feature_account.MoneyAmountUi
import uz.inha.chads.ui.feature_cards.helpers.CardNetwork
import uz.inha.chads.ui.feature_cards.helpers.CardUiHelpers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class CardUi(
    val id: String,
    val isPrimary: Boolean,
    val cardNumber: String,
    val expiration: String,
    val recentBalance: String,
    val balanceFlow: Flow<String>,
    val addressFirstLine: String,
    val addressSecondLine: String?,
    val dateAdded: String,
    val cardType: UiText,
    val cardColor: Color,
    val cardNetwork: CardNetwork
) {
    companion object {
        fun mock(
            cardColor: Color = Color(0xFFF05324),
        ): CardUi {
            val mockNumber = "2298126833989874"

            return CardUi(
                id = mockNumber,
                cardNumber = mockNumber.splitStringWithDivider(),
                expiration = "02/24",
                recentBalance = "\$2887.65",
                balanceFlow = flowOf("\$2887.65"),
                addressFirstLine = "2890 Pangandaran Street",
                addressSecondLine = null,
                dateAdded = "12 Jan 2021 22:12",
                cardType = UiText.DynamicString("Debit"),
                cardColor = cardColor,
                isPrimary = true,
                cardNetwork = CardNetwork.MASTERCARD
            )
        }

        fun mapFromDomain(
            card: PaymentCard,
            balanceFlow: Flow<String>? = null
        ): CardUi {
            val date = card.expiration.getFormattedDate("MM/yy")
            val recentBalance = MoneyAmountUi.mapFromDomain(card.recentBalance).amountStr

            return CardUi(
                id = card.cardId,
                cardNumber = card.cardNumber.splitStringWithDivider(),
                isPrimary = card.isPrimary,
                expiration = date,
                recentBalance = recentBalance,
                balanceFlow = balanceFlow ?: flowOf(recentBalance),
                addressFirstLine = card.addressFirstLine,
                addressSecondLine = card.addressSecondLine.ifBlank { null },
                dateAdded = card.addedDate.getFormattedDate("dd MMM yyyy HH:mm"),
                cardType = when (card.cardType) {
                    CardType.DEBIT -> UiText.StringResource(R.string.debit)
                    CardType.CREDIT -> UiText.StringResource(R.string.credit)
                },
                cardColor = when (card.cardType) {
                    CardType.DEBIT -> {
                        Color(0xFFF05324)
                    }

                    CardType.CREDIT -> {
                        Color(0xFF262627)
                    }
                },
                cardNetwork = CardUiHelpers.detectCardNetwork(card.cardNumber)
            )
        }
    }
}
