package uz.inha.chads.ui.feature_cards.dialog_card_picker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.inha.chads.domain.core.OperationResult
import uz.inha.chads.domain.features.cards.GetAllCardsUseCase
import uz.inha.chads.ui.core.error.asUiTextError
import uz.inha.chads.ui.feature_cards.model.CardUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardPickerViewModel(
    private val getAllCardsUseCase: GetAllCardsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CardPickerState())
    val state = _state.asStateFlow()

    init {
        _state.update {
            it.copy(isLoading = true)
        }
    }

    fun emitIntent(intent: CardPickerIntent) {
        when (intent) {
            is CardPickerIntent.LoadCards -> {
                _state.update {
                    it.copy(isLoading = true)
                }

                viewModelScope.launch {
                    val cards = OperationResult.runWrapped {
                        getAllCardsUseCase.execute()
                    }

                    when (cards) {
                        is OperationResult.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    cards = cards.data.map { card ->
                                        CardUi.mapFromDomain(card)
                                    },
                                    selectedCardId = intent.defaultSelectedCardId
                                )
                            }
                        }

                        is OperationResult.Failure -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    cards = null,
                                    error = cards.error.errorType.asUiTextError()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}