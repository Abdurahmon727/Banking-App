package uz.inha.chads.domain.features.profile

import uz.inha.chads.domain.features.profile.model.CompactProfile

interface ProfileRepository {
    suspend fun getCompactProfile(): CompactProfile
}