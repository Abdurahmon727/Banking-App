package uz.inha.chads.core.di.domain

import uz.inha.chads.domain.features.transactions.GetTransactionsUseCase
import uz.inha.chads.domain.features.transactions.ObserveTransactionStatusUseCase
import org.koin.dsl.module

val transactionsModule = module {
    factory { GetTransactionsUseCase(get()) }
    factory { ObserveTransactionStatusUseCase(get()) }
}