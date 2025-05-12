package uz.inha.chads.domain.features.onboarding

import uz.inha.chads.data.app.AppSettignsRepository

class PassOnboardingUseCase(
    private val settignsRepository: AppSettignsRepository
) {
    fun execute() {
        settignsRepository.setOnboardingPassed(viewed = true)
    }
}