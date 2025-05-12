package uz.inha.chads.core.di.domain

import uz.inha.chads.domain.features.profile.GetCompactProfileUseCase
import org.koin.dsl.module

val profileUseCasesModule = module {
    factory {  GetCompactProfileUseCase(profileRepository = get()) }
}