package uz.inha.chads.domain.features.cards

class RemoveCardUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(cardId: String) {
        cardsRepository.deleteCardById(cardId)
    }
}