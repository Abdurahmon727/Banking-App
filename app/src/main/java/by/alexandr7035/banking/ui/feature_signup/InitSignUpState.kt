package by.alexandr7035.banking.ui.feature_signup

import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.ui.feature_cards.screen_add_card.UiField
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class InitSignUpState(
    val fields: InitSignUpFields = InitSignUpFields(),
    val agreedTerms: Boolean = false,
    val isLoading: Boolean = false,
    val signUpBtnEnabled: Boolean = false,
    val initSignUpEvent: StateEventWithContent<OperationResult<Unit>> = consumed(),
) {
    data class InitSignUpFields(
        val fullName: UiField = UiField(""),
        val email: UiField = UiField(""),
        val password: UiField = UiField(""),
    )

    companion object {
        fun mock() = InitSignUpState(
            fields = InitSignUpFields(
                fullName = UiField("Abdurakhmon Dedamirzaev"),
                email = UiField("abdurahmon727.uz@gmail.com"),
                password = UiField("ABC_12345678"),
            ),
        )
    }
}

enum class InitSignUpFieldType {
    EMAIL, PASSWORD, FULL_NAME
}
