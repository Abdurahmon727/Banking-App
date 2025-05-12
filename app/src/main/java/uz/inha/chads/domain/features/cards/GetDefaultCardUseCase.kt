package uz.inha.chads.domain.features.cards

import uz.inha.chads.domain.features.cards.model.PaymentCard

class GetDefaultCardUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(): PaymentCard? {
        val cards = cardsRepository.getCards()

        return cards.find {
            it.isPrimary
        } ?: cards.firstOrNull()
    }
}