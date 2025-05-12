package uz.inha.chads.domain.features.app_lock

class CheckAppLockedWithBiometricsUseCase(
    private val appLockRepository: AppLockRepository
) {
    fun execute(): Boolean {
        return appLockRepository.checkIfAppLockedWithBiometrics()
    }
}