package uz.inha.chads.domain.features.cards

class SetCardAsPrimaryUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(cardId: String, setAsPrimary: Boolean) {
        return cardsRepository.markCardAsPrimary(cardId, setAsPrimary)
    }
}