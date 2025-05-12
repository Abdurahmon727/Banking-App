package uz.inha.chads.ui.feature_contacts.dialog_contact_picker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uz.inha.chads.domain.core.OperationResult
import uz.inha.chads.domain.features.contacts.GetContactsUseCase
import uz.inha.chads.ui.core.error.asUiTextError
import uz.inha.chads.ui.feature_contacts.model.ContactUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactPickerDialogViewModel(
    private val getContactsUseCase: GetContactsUseCase
): ViewModel() {
    private val _state = MutableStateFlow(ContactPickerDialogState())
    val state = _state.asStateFlow()

    fun emitIntent(intent: ContactPickerDialogIntent) {
        when (intent) {
            is ContactPickerDialogIntent.LoadContacts -> {
                _state.update {
                    it.copy(isLoading = true)
                }

                viewModelScope.launch {
                    val contacts = OperationResult.runWrapped {
                        getContactsUseCase.execute()
                    }

                    when (contacts) {
                        is OperationResult.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    contacts = contacts.data.map { card ->
                                        ContactUi.mapFromDomain(card)
                                    },
                                    selectedContactId = intent.defaultSelectedContactId
                                )
                            }
                        }

                        is OperationResult.Failure -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    contacts = null,
                                    error = contacts.error.errorType.asUiTextError()
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}