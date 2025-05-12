package uz.inha.chads.domain.features.cards

import uz.inha.chads.domain.features.cards.model.AddCardPayload

class AddCardUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(payload: AddCardPayload) {
        cardsRepository.addCard(payload)
    }
}