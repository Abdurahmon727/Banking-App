package uz.inha.chads.core.di.domain

import uz.inha.chads.domain.features.login.CheckIfLoggedInUseCase
import uz.inha.chads.domain.features.login.LoginWithEmailUseCase
import uz.inha.chads.domain.features.login.LogoutUseCase
import org.koin.dsl.module

val loginUseCasesModule = module {
    factory { LoginWithEmailUseCase(loginRepository = get()) }
    factory { LogoutUseCase(loginRepository = get()) }
    factory { CheckIfLoggedInUseCase(loginRepository = get()) }
}