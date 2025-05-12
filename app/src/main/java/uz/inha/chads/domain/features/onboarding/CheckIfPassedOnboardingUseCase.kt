package uz.inha.chads.domain.features.onboarding

import uz.inha.chads.data.app.AppSettignsRepository

class CheckIfPassedOnboardingUseCase(
    private val settignsRepository: AppSettignsRepository
) {
    fun execute(): Boolean {
        return settignsRepository.isOnboardingPassed()
    }
}