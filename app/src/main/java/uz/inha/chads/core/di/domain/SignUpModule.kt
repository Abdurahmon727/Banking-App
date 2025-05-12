package uz.inha.chads.core.di.domain

import uz.inha.chads.domain.features.signup.ConfirmSignUpWithEmailUseCase
import uz.inha.chads.domain.features.signup.SignUpWithEmailUseCase
import org.koin.dsl.module

val signUpModule = module {
    factory { ConfirmSignUpWithEmailUseCase(signUpRepository = get()) }
    factory { SignUpWithEmailUseCase(signUpRepository = get()) }
}