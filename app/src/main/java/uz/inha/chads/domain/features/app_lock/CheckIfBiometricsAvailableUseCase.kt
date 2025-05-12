package uz.inha.chads.domain.features.app_lock

import uz.inha.chads.domain.features.app_lock.model.BiometricsAvailability

class CheckIfBiometricsAvailableUseCase(
    private val appLockRepository: AppLockRepository
) {
    fun execute(): BiometricsAvailability {
        return appLockRepository.checkBiometricsAvailable()
    }
}