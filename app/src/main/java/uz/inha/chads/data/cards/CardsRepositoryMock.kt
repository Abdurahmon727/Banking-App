package uz.inha.chads.data.cards

import uz.inha.chads.data.cards.cache.CardEntity
import uz.inha.chads.data.cards.cache.CardsDao
import uz.inha.chads.domain.core.AppError
import uz.inha.chads.domain.core.ErrorType
import uz.inha.chads.domain.features.account.model.MoneyAmount
import uz.inha.chads.domain.features.cards.model.AddCardPayload
import uz.inha.chads.domain.features.cards.CardsRepository
import uz.inha.chads.domain.features.cards.model.CardType
import uz.inha.chads.domain.features.cards.model.PaymentCard
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class CardsRepositoryMock(
    private val cardsDao: CardsDao,
    private val coroutineDispatcher: CoroutineDispatcher
) : CardsRepository {
    override suspend fun getCards(): List<PaymentCard> = withContext(coroutineDispatcher) {
        delay(MOCK_DELAY)

        return@withContext cardsDao.getCards().map { cardEntity ->
            mapCachedCardToDomain(cardEntity)
        }
    }

    override suspend fun addCard(data: AddCardPayload) = withContext(coroutineDispatcher) {
        val card = cardsDao.getCardByNumber(data.cardNumber)

        if (card == null) {
            delay(MOCK_DELAY)

            val entity = mapAddCardPayloadToCache(data)
            cardsDao.addCard(entity)
        }
        else {
            throw AppError(ErrorType.CARD_ALREADY_ADDED)
        }
    }

    override suspend fun getCardById(id: String): PaymentCard = withContext(coroutineDispatcher) {
        val cardEntity = cardsDao.getCardByNumber(id) ?: throw AppError(ErrorType.CARD_NOT_FOUND)
        delay(MOCK_DELAY)
        return@withContext mapCachedCardToDomain(cardEntity)
    }

    private fun mapCachedCardToDomain(cardEntity: CardEntity) = PaymentCard(
        cardId = cardEntity.number,
        cardNumber = cardEntity.number,
        isPrimary = cardEntity.isPrimary,
        cardHolder = cardEntity.cardHolder,
        addressFirstLine = cardEntity.addressFirstLine,
        addressSecondLine = cardEntity.addressSecondLine,
        expiration = cardEntity.expiration,
        addedDate = cardEntity.addedDate,
        recentBalance = MoneyAmount(cardEntity.recentBalance),
        cardType = cardEntity.cardType
    )

    private fun mapAddCardPayloadToCache(addCardPayload: AddCardPayload): CardEntity {
        val type = MockCardConstants.cardTypeByNumber(addCardPayload.cardNumber) ?: CardType.DEBIT

        return CardEntity(
            number = addCardPayload.cardNumber,
            isPrimary = false,
            cardHolder = addCardPayload.cardHolder,
            addressFirstLine = addCardPayload.addressFirstLine,
            addressSecondLine = addCardPayload.addressSecondLine,
            expiration = addCardPayload.expirationDate,
            addedDate = System.currentTimeMillis(),
            recentBalance = MOCK_CARD_INITIAL_BALANCE,
            cardType = type
        )
    }

    override suspend fun deleteCardById(id: String) {
        val cardEntity = cardsDao.getCardByNumber(id) ?: throw AppError(ErrorType.CARD_NOT_FOUND)
        delay(MOCK_DELAY)
        cardsDao.deleteCard(cardEntity)
    }

    override suspend fun markCardAsPrimary(cardId: String, isPrimary: Boolean) {
        when (isPrimary) {
            true ->  cardsDao.markCardAsPrimary(cardId)
            false -> cardsDao.unmarkCardAsPrimary(cardId)
        }
    }

    companion object {
        private const val MOCK_DELAY = 500L
        private const val MOCK_CARD_INITIAL_BALANCE = 0f
    }
}