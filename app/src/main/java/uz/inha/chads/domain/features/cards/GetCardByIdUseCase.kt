package uz.inha.chads.domain.features.cards

import uz.inha.chads.domain.features.cards.model.PaymentCard

class GetCardByIdUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(cardId: String): PaymentCard {
        return cardsRepository.getCardById(cardId)
    }
}