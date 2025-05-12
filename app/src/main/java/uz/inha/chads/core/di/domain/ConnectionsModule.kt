package uz.inha.chads.core.di.domain

import uz.inha.chads.domain.features.connections.LoadUserFromQrCodeUseCase
import uz.inha.chads.domain.features.qr_codes.GenerateQrCodeUseCase
import org.koin.dsl.module

val connectionsModule = module {
    factory { GenerateQrCodeUseCase() }
    factory { LoadUserFromQrCodeUseCase(
        contactsRepository = get()
    ) }
}