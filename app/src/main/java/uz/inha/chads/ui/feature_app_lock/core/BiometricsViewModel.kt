package uz.inha.chads.ui.feature_app_lock.core

import uz.inha.chads.ui.feature_app_lock.core.biometrics.BiometricsIntent

interface BiometricsViewModel {
    fun emitBiometricsIntent(intent: BiometricsIntent)
}