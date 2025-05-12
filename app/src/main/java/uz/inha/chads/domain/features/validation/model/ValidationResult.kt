package uz.inha.chads.domain.features.validation.model

import uz.inha.chads.domain.core.ErrorType

data class ValidationResult(
    val isValid: Boolean,
    val validationError: ErrorType? = null
)
