package uz.inha.chads.ui.feature_signup

sealed class InitSIgnUpIntent {
    object EnterScreen : InitSIgnUpIntent()
    data class ToggleTermsAgreed(val agreed: Boolean) : InitSIgnUpIntent()
    data class FieldChanged(
        val fieldType: InitSignUpFieldType,
        val fieldValue: String
    ) : InitSIgnUpIntent()

    object SignUpConfirm : InitSIgnUpIntent()
}