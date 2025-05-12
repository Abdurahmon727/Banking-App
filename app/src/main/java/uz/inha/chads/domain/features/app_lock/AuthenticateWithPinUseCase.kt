package uz.inha.chads.domain.features.app_lock

import uz.inha.chads.domain.features.app_lock.model.AuthenticationResult

class AuthenticateWithPinUseCase(
    private val appLockRepository: AppLockRepository
) {
    fun execute(pin: String): AuthenticationResult {
        return appLockRepository.authenticateWithPin(pin)
    }
}