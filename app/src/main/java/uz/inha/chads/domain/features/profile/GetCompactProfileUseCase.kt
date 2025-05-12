package uz.inha.chads.domain.features.profile

import uz.inha.chads.domain.features.profile.model.CompactProfile

class GetCompactProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend fun execute(): CompactProfile {
        return profileRepository.getCompactProfile()
    }
}