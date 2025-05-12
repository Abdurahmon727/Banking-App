package uz.inha.chads.ui.feature_cards.screen_card_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.inha.chads.domain.core.ErrorType
import uz.inha.chads.domain.core.OperationResult
import uz.inha.chads.domain.features.account.GetCardBalanceObservableUseCase
import uz.inha.chads.domain.features.cards.GetCardByIdUseCase
import uz.inha.chads.domain.features.cards.RemoveCardUseCase
import uz.inha.chads.domain.features.cards.SetCardAsPrimaryUseCase
import uz.inha.chads.domain.features.cards.model.PaymentCard
import uz.inha.chads.ui.core.error.asUiTextError
import uz.inha.chads.ui.feature_account.MoneyAmountUi
import uz.inha.chads.ui.feature_cards.model.CardUi
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardDetailsViewModel(
    private val getCardByIdUseCase: GetCardByIdUseCase,
    private val deleteCardByNumberUseCase: RemoveCardUseCase,
    private val getCardBalanceObservableUseCase: GetCardBalanceObservableUseCase,
    private val setCardAsPrimaryUseCase: SetCardAsPrimaryUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<CardDetailsState> = MutableStateFlow(CardDetailsState())
    val state = _state.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        reduceError(ErrorType.fromThrowable(throwable))
    }

    fun emitIntent(intent: CardDetailsIntent) {
        when (intent) {
            is CardDetailsIntent.EnterScreen -> {
                reduceScreenLoading()

                viewModelScope.launch(errorHandler) {
                    val card = getCardByIdUseCase.execute(intent.cardId)
                    val cardBalanceFlow = getCardBalanceObservableUseCase
                        .execute(intent.cardId)
                        .map { MoneyAmountUi.mapFromDomain(it).amountStr }
                        .catch { reduceError(ErrorType.fromThrowable(it)) }

                    reduceSuccessData(card, cardBalanceFlow)
                }
            }

            is CardDetailsIntent.ToggleDeleteCardDialog -> {
                reduceDeleteCardDialog(
                    isShown = intent.isDialogShown
                )
            }

            is CardDetailsIntent.ConfirmDeleteCard -> {
                reduceDeleteCard()
            }

            is CardDetailsIntent.SetCardAsPrimary -> {
                viewModelScope.launch(errorHandler) {
                    val res = OperationResult.runWrapped {
                        setCardAsPrimaryUseCase.execute(
                            cardId = intent.cardId,
                            setAsPrimary = intent.makePrimary
                        )
                    }

                    when (res) {
                        is OperationResult.Success -> {
                            _state.update {
                                it.copy(
                                    setCardAsPrimaryEvent = triggered(res),
                                    card = it.card?.copy(isPrimary = intent.makePrimary)
                                )
                            }
                        }

                        is OperationResult.Failure -> {
                            _state.update {
                                it.copy(
                                    setCardAsPrimaryEvent = triggered(res),
                                )
                            }
                        }
                    }
                }
            }
        }
    }


    private fun reduceScreenLoading() {
        _state.update {
            it.copy(
                showCardSkeleton = true
            )
        }
    }

    private fun reduceSuccessData(
        card: PaymentCard,
        balanceFlow: Flow<String>
    ) {
        _state.update {
            it.copy(
                showCardSkeleton = false,
                card = CardUi.mapFromDomain(
                    card = card,
                    balanceFlow = balanceFlow,
                )
            )
        }
    }

    private fun reduceDeleteCardDialog(
        isShown: Boolean
    ) {
        _state.update {
            it.copy(
                showDeleteCardDialog = isShown
            )
        }
    }

    private fun reduceDeleteCard() {
        viewModelScope.launch(errorHandler) {
            _state.update {
                it.copy(showLoading = true)
            }

            _state.value.card?.id?.let { cardId ->
                val res = OperationResult.runWrapped {
                    deleteCardByNumberUseCase.execute(cardId)
                }

                when (res) {
                    is OperationResult.Success -> {
                        _state.update {
                            it.copy(
                                card = it.card?.copy(
                                    balanceFlow = emptyFlow()
                                ),
                                cardDeletedResultEvent = triggered(res)
                            )
                        }
                    }

                    is OperationResult.Failure -> {
                        _state.update {
                            it.copy(
                                showLoading = false,
                                cardDeletedResultEvent = triggered(res)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun reduceError(error: ErrorType) {
        _state.update {
            it.copy(
                showCardSkeleton = false,
                error = error.asUiTextError()
            )
        }
    }

    fun consumeDeleteResultEvent() {
        _state.update {
            it.copy(
                showLoading = false,
                cardDeletedResultEvent = consumed()
            )
        }
    }

    fun consumeSetCardAsDefaultEvent() {
        _state.update {
            it.copy(
                showLoading = false,
                setCardAsPrimaryEvent = consumed()
            )
        }
    }
}