package uz.inha.chads.ui.feature_savings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.inha.chads.domain.core.ErrorType
import uz.inha.chads.domain.core.OperationResult
import uz.inha.chads.domain.features.savings.model.Saving
import uz.inha.chads.domain.features.savings.GetAllSavingsUseCase
import uz.inha.chads.ui.core.error.asUiTextError
import uz.inha.chads.ui.feature_savings.model.SavingUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SavingsViewModel(
    private val getAllSavingsUseCase: GetAllSavingsUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<SavingsListState> = MutableStateFlow(
        SavingsListState.Loading
    )

    val state = _state.asStateFlow()

    fun emitIntent(intent: SavingsListIntent) {
        when (intent) {
            SavingsListIntent.EnterScreen -> {
                reduceLoading()

                viewModelScope.launch {
                    val res = OperationResult.runWrapped {
                        getAllSavingsUseCase.execute()
                    }

                    when (res) {
                        is OperationResult.Success -> {
                            reduceData(res.data)
                        }

                        is OperationResult.Failure -> {
                            reduceError(res.error.errorType)
                        }
                    }
                }
            }
        }
    }

    private fun reduceLoading() {
        _state.value = SavingsListState.Loading
    }

    private fun reduceData(savings: List<Saving>) {
        _state.value = SavingsListState.Success(savings = savings.map {
            SavingUi.mapFromDomain(it)
        })
    }

    private fun reduceError(errorType: ErrorType) {
        _state.value = SavingsListState.Error(error = errorType.asUiTextError())
    }
}