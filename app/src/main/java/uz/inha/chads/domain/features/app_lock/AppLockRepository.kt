package uz.inha.chads.domain.features.app_lock

import uz.inha.chads.domain.features.app_lock.model.AuthenticationResult
import uz.inha.chads.domain.features.app_lock.model.BiometricsAvailability

interface AppLockRepository {
    fun setupAppLock(pinCode: String)
    fun authenticateWithPin(pin: String): AuthenticationResult
    fun checkIfAppLocked(): Boolean
    fun checkBiometricsAvailable(): BiometricsAvailability
    fun setupLockWithBiometrics(isLocked: Boolean)
    fun checkIfAppLockedWithBiometrics(): Boolean
}