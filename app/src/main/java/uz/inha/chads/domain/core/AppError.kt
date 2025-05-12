package uz.inha.chads.domain.core

data class AppError(val errorType: ErrorType): Exception()