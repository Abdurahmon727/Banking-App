package uz.inha.chads.ui.feature_login

import uz.inha.chads.domain.core.OperationResult
import uz.inha.chads.ui.feature_cards.screen_add_card.UiField
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class LoginScreenState(
    val formFields: LoginFormFields = LoginFormFields(),
    val isLoading: Boolean = false,
    val loginEvent: StateEventWithContent<OperationResult<Unit>> = consumed()
) {
    companion object {
        fun mock() = LoginScreenState(
            formFields = LoginFormFields(
                loginField = UiField("abdurahmon727.uz@gmail.com"),
                passwordField = UiField("ABC_12345678")
            )
        )
    }
}

data class LoginFormFields(
    val loginField: UiField = UiField("", null),
    val passwordField: UiField = UiField("", null),
)

enum class LoginFieldType {
    EMAIL, PASSWORD
}
