package uz.inha.chads.core.di.domain

import uz.inha.chads.domain.features.onboarding.CheckIfPassedOnboardingUseCase
import uz.inha.chads.domain.features.onboarding.PassOnboardingUseCase
import org.koin.dsl.module

val onboardingModule = module {
    factory {
        CheckIfPassedOnboardingUseCase(
            settignsRepository = get()
        )
    }
    factory {
        PassOnboardingUseCase(
            settignsRepository = get()
        )
    }
}