package uz.inha.chads.domain.features.profile.model

data class CompactProfile(
    val id: String,
    val firstName: String,
    val lastName: String,
    val nickName: String,
    val email: String,
    val profilePicUrl: String,
    val tier: ProfileTier,
)