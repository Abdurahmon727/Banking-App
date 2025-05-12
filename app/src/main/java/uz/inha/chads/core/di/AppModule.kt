package uz.inha.chads.core.di

import uz.inha.chads.core.di.data.dataModule
import uz.inha.chads.core.di.data.workerModule
import uz.inha.chads.core.di.domain.accountUseCasesModule
import uz.inha.chads.core.di.domain.appLockUseCasesModule
import uz.inha.chads.core.di.domain.cardUseCasesModule
import uz.inha.chads.core.di.domain.connectionsModule
import uz.inha.chads.core.di.domain.contactsModule
import uz.inha.chads.core.di.domain.loginUseCasesModule
import uz.inha.chads.core.di.domain.onboardingModule
import uz.inha.chads.core.di.domain.otpUseCasesModule
import uz.inha.chads.core.di.domain.profileUseCasesModule
import uz.inha.chads.core.di.domain.savingsUseCasesModule
import uz.inha.chads.core.di.domain.signUpModule
import uz.inha.chads.core.di.domain.transactionsModule
import uz.inha.chads.core.di.domain.validationUseCasesModule
import uz.inha.chads.core.di.presentation.presentationModule
import org.koin.dsl.module

val appModule = module {
    includes(appLockUseCasesModule)

    includes(loginUseCasesModule)
    includes(signUpModule)

    includes(cardUseCasesModule)
    includes(validationUseCasesModule)
    includes(savingsUseCasesModule)
    includes(profileUseCasesModule)
    includes(onboardingModule)
    includes(otpUseCasesModule)
    includes(accountUseCasesModule)
    includes(transactionsModule)
    includes(contactsModule)
    includes(connectionsModule)

    includes(dataModule)
    includes(presentationModule)
    includes(workerModule)
}