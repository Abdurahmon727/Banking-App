package uz.inha.chads.core.di.domain

import uz.inha.chads.domain.features.account.GetCardBalanceObservableUseCase
import uz.inha.chads.domain.features.cards.AddCardUseCase
import uz.inha.chads.domain.features.cards.GetAllCardsUseCase
import uz.inha.chads.domain.features.cards.GetCardByIdUseCase
import uz.inha.chads.domain.features.cards.GetDefaultCardUseCase
import uz.inha.chads.domain.features.cards.GetHomeCardsUseCase
import uz.inha.chads.domain.features.cards.SetCardAsPrimaryUseCase
import uz.inha.chads.domain.features.cards.RemoveCardUseCase
import org.koin.dsl.module

val cardUseCasesModule = module {
    factory { GetAllCardsUseCase(get()) }
    factory { AddCardUseCase(get()) }
    factory { GetHomeCardsUseCase(get()) }
    factory { GetCardByIdUseCase(get()) }
    factory { RemoveCardUseCase(get()) }
    factory { GetDefaultCardUseCase(get()) }
    factory { GetCardBalanceObservableUseCase(get()) }
    factory { SetCardAsPrimaryUseCase(get()) }
}