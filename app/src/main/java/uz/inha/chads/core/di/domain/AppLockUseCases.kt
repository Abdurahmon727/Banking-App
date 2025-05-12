package uz.inha.chads.core.di.domain

import uz.inha.chads.domain.features.app_lock.AuthenticateWithPinUseCase
import uz.inha.chads.domain.features.app_lock.CheckAppLockUseCase
import uz.inha.chads.domain.features.app_lock.CheckAppLockedWithBiometricsUseCase
import uz.inha.chads.domain.features.app_lock.CheckIfBiometricsAvailableUseCase
import uz.inha.chads.domain.features.app_lock.SetupAppLockUseCase
import uz.inha.chads.domain.features.app_lock.SetupAppLockedWithBiometricsUseCase
import org.koin.dsl.module

val appLockUseCasesModule = module {
    factory { AuthenticateWithPinUseCase(
        appLockRepository = get()
    ) }

    factory { CheckAppLockUseCase(
        appLockRepository = get()
    ) }

    factory {
        SetupAppLockUseCase(
            appLockRepository = get()
        )
    }

    factory {
        SetupAppLockedWithBiometricsUseCase(
            appLockRepository = get()
        )
    }

    factory {
        CheckAppLockedWithBiometricsUseCase(
            appLockRepository = get()
        )
    }

    factory {
        CheckIfBiometricsAvailableUseCase(
            appLockRepository = get()
        )
    }
}