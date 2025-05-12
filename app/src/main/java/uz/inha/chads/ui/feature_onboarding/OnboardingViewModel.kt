package uz.inha.chads.ui.feature_onboarding

import androidx.lifecycle.ViewModel
import uz.inha.chads.domain.features.onboarding.PassOnboardingUseCase

class OnboardingViewModel(
    private val passOnboardingUseCase: PassOnboardingUseCase
): ViewModel() {

    fun emitIntent(onboardingIntent: OnboardingIntent) {
        when (onboardingIntent) {
            is OnboardingIntent.CompleteOnboarding -> {
                passOnboardingUseCase.execute()
            }
        }
    }
}