package uz.inha.chads.ui.feature_cards.screen_add_card

import uz.inha.chads.data.cards.MockCardConstants
import uz.inha.chads.domain.core.OperationResult
import uz.inha.chads.ui.core.extensions.getFormattedDate
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class AddCardState(
    val formFields: AddCardFormFields = AddCardFormFields(),
    val isLoading: Boolean = false,
    val showDatePicker: Boolean = false,
    val cardSavedEvent: StateEventWithContent<OperationResult<Unit>> = consumed()
) {
    companion object {
        fun mock(
            isLoading: Boolean = false, showDatePicker: Boolean = false
        ): AddCardState {
            // + 365 days
            val randomMockCard = MockCardConstants.randomCard()
            val mockExpiration = System.currentTimeMillis() + 31556926000L

            return AddCardState(
                formFields = AddCardFormFields(
                    cardNumber = UiField(randomMockCard.first),
                    cardHolder = UiField("Abdurakhmon Dedamirzaev"),
                    addressFirstLine = UiField("2890 Mirzo Ulugbek Street"),
                    addressSecondLine = UiField(""),
                    cvvCode = UiField("761"),
                    expirationDateTimestamp = mockExpiration,
                    expirationDate = UiField(mockExpiration.getFormattedDate("dd MMM yyyy"))
                ), isLoading = isLoading, showDatePicker = showDatePicker
            )
        }
    }
}
