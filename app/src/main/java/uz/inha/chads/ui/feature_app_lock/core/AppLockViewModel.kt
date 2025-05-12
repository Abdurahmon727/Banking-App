package uz.inha.chads.ui.feature_app_lock.core

interface AppLockViewModel {
    val pinLength: Int

    fun emitAppLockIntent(intent: AppLockIntent)
}