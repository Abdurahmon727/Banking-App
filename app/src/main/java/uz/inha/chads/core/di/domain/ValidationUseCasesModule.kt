package uz.inha.chads.core.di.domain

import uz.inha.chads.domain.features.validation.ValidateBillingAddressUseCase
import uz.inha.chads.domain.features.validation.ValidateCardExpirationUseCase
import uz.inha.chads.domain.features.validation.ValidateCardHolderUseCase
import uz.inha.chads.domain.features.validation.ValidateCardNumberUseCase
import uz.inha.chads.domain.features.validation.ValidateCvvCodeUseCase
import uz.inha.chads.domain.features.validation.ValidateEmailUseCase
import uz.inha.chads.domain.features.validation.ValidatePasswordUseCase
import org.koin.dsl.module

val validationUseCasesModule = module {
    factory { ValidateCardNumberUseCase() }
    factory { ValidateCvvCodeUseCase() }
    factory { ValidateCardExpirationUseCase() }
    factory { ValidateBillingAddressUseCase() }
    factory { ValidateCardHolderUseCase() }
    factory { ValidatePasswordUseCase() }
    factory { ValidateEmailUseCase() }
}