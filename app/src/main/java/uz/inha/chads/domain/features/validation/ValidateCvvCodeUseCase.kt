package uz.inha.chads.domain.features.validation

import uz.inha.chads.domain.core.ErrorType
import uz.inha.chads.domain.features.validation.model.ValidationResult

class ValidateCvvCodeUseCase {
    fun execute(cvv: String): ValidationResult {
        return if (cvv.isBlank()) {
            ValidationResult(isValid = false, validationError = ErrorType.FIELD_IS_EMPTY)
        } else {
            // Regular expression for 3 or 4 digits
            val cvvPattern = "\\d{3,4}".toRegex()

            if (cvvPattern.matches(cvv)) {
                ValidationResult(isValid = true, validationError = null)
            } else {
                ValidationResult(isValid = false, validationError = ErrorType.INVALID_CVV)
            }
        }
    }
}