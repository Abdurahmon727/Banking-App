package uz.inha.chads.core.di.domain

import uz.inha.chads.domain.features.otp.RequestOtpGenerationUseCase
import org.koin.dsl.module

val otpUseCasesModule = module {
    factory { RequestOtpGenerationUseCase(
        otpRepository = get()
    ) }
}