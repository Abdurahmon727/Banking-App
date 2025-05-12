package uz.inha.chads.core.di.domain

import uz.inha.chads.domain.features.account.GetTotalAccountBalanceUseCase
import uz.inha.chads.domain.features.account.account_topup.GetSuggestedTopUpValuesUseCase
import uz.inha.chads.domain.features.account.account_topup.TopUpAccountUseCase
import uz.inha.chads.domain.features.account.send_money.GetSuggestedSendValuesForCardBalance
import uz.inha.chads.domain.features.account.send_money.SendMoneyUseCase
import org.koin.dsl.module

val accountUseCasesModule = module {
    factory {
        GetTotalAccountBalanceUseCase(
            accountRepository = get()
        )
    }

    factory {
        GetSuggestedTopUpValuesUseCase()
    }

    factory {
        GetSuggestedSendValuesForCardBalance()
    }

    factory {
        TopUpAccountUseCase(
            transactionRepository = get()
        )
    }

    factory {
        SendMoneyUseCase(
            transactionRepository = get()
        )
    }
}