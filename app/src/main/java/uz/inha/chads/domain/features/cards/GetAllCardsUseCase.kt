package uz.inha.chads.domain.features.cards

import uz.inha.chads.domain.features.cards.model.PaymentCard

class GetAllCardsUseCase(private val cardsRepository: CardsRepository) {
    suspend fun execute(): List<PaymentCard> {
        val all = cardsRepository.getCards()

        val primary = all
            .filter { it.isPrimary }
            .sortedByDescending { it.addedDate }

        val other = all
            .filter { !it.isPrimary }
            .sortedByDescending { it.addedDate }

        return primary + other
    }
}