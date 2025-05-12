package uz.inha.chads.domain.features.app_lock

class SetupAppLockedWithBiometricsUseCase(
    private val appLockRepository: AppLockRepository
) {
    fun execute(isLocked: Boolean = true) {
        return appLockRepository.setupLockWithBiometrics(isLocked)
    }
}