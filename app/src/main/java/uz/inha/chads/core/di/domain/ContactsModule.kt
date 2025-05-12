package uz.inha.chads.core.di.domain

import uz.inha.chads.domain.features.contacts.GetContactByIdUseCase
import uz.inha.chads.domain.features.contacts.GetContactsUseCase
import uz.inha.chads.domain.features.contacts.GetRecentContactUseCase
import org.koin.dsl.module

val contactsModule = module {
    factory { GetContactsUseCase(get()) }
    factory { GetContactByIdUseCase(get()) }
    factory { GetRecentContactUseCase(get()) }
}