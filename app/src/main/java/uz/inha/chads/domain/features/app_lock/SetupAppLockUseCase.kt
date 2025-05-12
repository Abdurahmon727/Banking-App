package uz.inha.chads.domain.features.app_lock

class SetupAppLockUseCase(
    private val appLockRepository: AppLockRepository
) {
    fun execute(pinCode: String) {
        return appLockRepository.setupAppLock(pinCode)
    }
}