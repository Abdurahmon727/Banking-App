package uz.inha.chads.ui.feature_profile.model

import uz.inha.chads.domain.features.profile.model.CompactProfile
import uz.inha.chads.domain.features.profile.model.ProfileTier
import uz.inha.chads.ui.core.extensions.splitStringWithDivider

data class ProfileUi(
    val fullName: String,
    val nickName: String,
    val id: String,
    val email: String,
    val profilePicUrl: String,
    val tier: String,
) {
    companion object {
        fun mock() = ProfileUi(
            fullName = "Abdurakhmon Dedamirzaev",
            nickName = "@abdurahmon",
            id = "14918248",
            email = "abdurahmon727.uz@gmail.com",
            profilePicUrl = "https://api.dicebear.com/7.x/open-peeps/svg?seed=Bailey",
            tier = "Premium"
        )

        fun mapFromDomain(profile: CompactProfile) = ProfileUi(
            fullName = "${profile.firstName} ${profile.lastName}",
            nickName = profile.nickName,
            id = profile.id.splitStringWithDivider(),
            email = profile.email,
            profilePicUrl = profile.profilePicUrl,
            tier = when (profile.tier) {
                ProfileTier.BASIC -> "Basic"
                ProfileTier.PREMIUM -> "Premium"
            }
        )
    }
}
