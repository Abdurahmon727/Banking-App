package uz.inha.chads.data.profile

import uz.inha.chads.domain.features.profile.model.CompactProfile
import uz.inha.chads.domain.features.profile.ProfileRepository
import uz.inha.chads.domain.features.profile.model.ProfileTier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ProfileRepositoryMock(
    private val dispatcher: CoroutineDispatcher
) : ProfileRepository {
    override suspend fun getCompactProfile(): CompactProfile = withContext(dispatcher) {
        delay(MOCK_DELAY)

        return@withContext CompactProfile(
            id = "089621027821",
            firstName = "Abdurakhmon",
            lastName = "Dedamirzaev",
            nickName = "@abdurahmon",
            email = "abdurahmon727.uz@gmail.com",
            profilePicUrl = "https://api.dicebear.com/7.x/open-peeps/svg?seed=Bailey",
            tier = ProfileTier.PREMIUM,
        )
    }

    companion object {
        private const val MOCK_DELAY = 300L
    }
}