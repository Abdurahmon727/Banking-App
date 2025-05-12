package uz.inha.chads.ui.feature_contacts.scanned_contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.inha.chads.domain.core.ErrorType
import uz.inha.chads.domain.core.OperationResult
import uz.inha.chads.domain.features.connections.LoadUserFromQrCodeUseCase
import uz.inha.chads.ui.core.error.asUiTextError
import uz.inha.chads.ui.feature_contacts.model.ContactUi
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScannedContactViewModel(
    private val loadUserFromQrCodeUseCase: LoadUserFromQrCodeUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ScannedContactScreenState())
    val state = _state.asStateFlow()

    fun emitIntent(intent: ScannedContactIntent) {
        when (intent) {
            is ScannedContactIntent.LoadContact -> {
                _state.update {
                    it.copy(
                        isContactLoading = true,
                        isLoading = false,
                        error = null
                    )
                }

                viewModelScope.launch {
                    val res = OperationResult.runWrapped {
                        loadUserFromQrCodeUseCase.execute(intent.qrCode)
                    }

                    when (res) {
                        is OperationResult.Success -> {
                            _state.update {
                                it.copy(
                                    isContactLoading = false,
                                    contact = ContactUi.mapFromDomain(res.data)
                                )
                            }
                        }

                        is OperationResult.Failure -> {
                            if (res.error.errorType == ErrorType.USER_NOT_FOUND) {
                                _state.update {
                                    it.copy(
                                        isContactLoading = false,
                                        error = null,
                                        addContactResEvent = triggered(res)
                                    )
                                }
                            }
                            else {
                                _state.update {
                                    it.copy(
                                        isContactLoading = false,
                                        error = res.error.errorType.asUiTextError()
                                    )
                                }
                            }
                        }
                    }
                }
            }

            is ScannedContactIntent.AddContact -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }

                    delay(1000)

                    _state.update {
                        it.copy(
                            isLoading = false,
                            addContactResEvent = triggered(OperationResult.Success(Unit))
                        )
                    }
                }
            }
        }
    }

    fun consumeContactAddedEvent() {
        _state.update {
            it.copy(
                addContactResEvent = consumed()
            )
        }
    }
}