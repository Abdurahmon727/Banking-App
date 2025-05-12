package uz.inha.chads.core.di.domain

import uz.inha.chads.domain.features.savings.GetAllSavingsUseCase
import uz.inha.chads.domain.features.savings.GetHomeSavingsUseCase
import uz.inha.chads.domain.features.savings.GetSavingByIdUseCase
import org.koin.dsl.module

val savingsUseCasesModule = module {
    factory { GetAllSavingsUseCase(savingsRepository = get()) }
    factory { GetHomeSavingsUseCase(savingsRepository = get()) }
    factory { GetSavingByIdUseCase(savingsRepository = get()) }
}