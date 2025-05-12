package uz.inha.chads.core.di.presentation

import uz.inha.chads.ui.app_host.AppViewModel
import uz.inha.chads.ui.core.notifications.TransactionNotificationHelper
import uz.inha.chads.ui.core.permissions.PermissionHelper
import uz.inha.chads.ui.feature_account.action_send.SendMoneyViewModel
import uz.inha.chads.ui.feature_account.action_topup.TopUpScreenViewModel
import uz.inha.chads.ui.feature_app_lock.lock_screen.LockScreenViewModel
import uz.inha.chads.ui.feature_app_lock.setup_applock.biometrics.EnableBiometricsViewModel
import uz.inha.chads.ui.feature_app_lock.setup_applock.pin.CreatePinViewModel
import uz.inha.chads.ui.feature_app_settings.AppSettingsViewModel
import uz.inha.chads.ui.feature_cards.dialog_card_picker.CardPickerViewModel
import uz.inha.chads.ui.feature_cards.screen_add_card.AddCardViewModel
import uz.inha.chads.ui.feature_cards.screen_card_details.CardDetailsViewModel
import uz.inha.chads.ui.feature_cards.screen_card_list.CardListViewModel
import uz.inha.chads.ui.feature_contacts.dialog_contact_picker.ContactPickerDialogViewModel
import uz.inha.chads.ui.feature_contacts.scanned_contact.ScannedContactViewModel
import uz.inha.chads.ui.feature_home.HomeViewModel
import uz.inha.chads.ui.feature_login.LoginViewModel
import uz.inha.chads.ui.feature_onboarding.OnboardingViewModel
import uz.inha.chads.ui.feature_profile.ProfileViewModel
import uz.inha.chads.ui.feature_qr_codes.DisplayQrViewModel
import uz.inha.chads.ui.feature_savings.SavingsViewModel
import uz.inha.chads.ui.feature_savings.screen_saving_details.SavingDetailsViewModel
import uz.inha.chads.ui.feature_signup.InitSignUpViewModel
import uz.inha.chads.ui.feature_signup.confirm_signup.ConfirmEmailSignUpVIewModel
import uz.inha.chads.ui.feature_transactions.TransactionHistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    single {
        TransactionNotificationHelper(
            applicationContext = get()
        )
    }

    single {
        PermissionHelper(
            appSettings = get()
        )
    }

    viewModel {
        OnboardingViewModel(
            passOnboardingUseCase = get()
        )
    }

    viewModel {
        LoginViewModel(
            loginWithEmailUseCase = get(),
            validateEmailUseCase = get(),
            validatePasswordUseCase = get()
        )
    }

    viewModel {
        AppViewModel(
            checkIfLoggedInUseCase = get(),
            checkIfPassedOnboardingUseCase = get(),
            checkAppLockUseCase = get()
        )
    }

    viewModel {
        ProfileViewModel(
            getCompactProfileUseCase = get(),
            logoutUseCase = get()
        )
    }

    viewModel {
        HomeViewModel(
            getHomeCardsUseCase = get(),
            getHomeSavingsUseCase = get(),
            getCompactProfileUseCase = get(),
            getTotalAccountBalanceUseCase = get(),
            getCardBalanceObservableUseCase = get()
        )
    }

    viewModel {
        CardListViewModel(
            getAllCardsUseCase = get(),
            getCardBalanceObservableUseCase = get()
        )
    }

    viewModel {
        CardDetailsViewModel(
            getCardByIdUseCase = get(),
            deleteCardByNumberUseCase = get(),
            getCardBalanceObservableUseCase = get(),
            setCardAsPrimaryUseCase = get()
        )
    }

    viewModel {
        AddCardViewModel(
            validateCardNumberUseCase = get(),
            validateCvvCodeUseCase = get(),
            validateCardExpirationUseCase = get(),
            validateCardHolderUseCase = get(),
            validateBillingAddressUseCase = get(),
            addCardUseCase = get()
        )
    }

    viewModel {
        SavingsViewModel(
            getAllSavingsUseCase = get()
        )
    }

    viewModel {
        SavingDetailsViewModel(
            getSavingByIdUseCase = get(),
            getCardByIdUseCase = get()
        )
    }

    viewModel {
        InitSignUpViewModel(
            signUpWithEmailUseCase = get(),
            validatePasswordUseCase = get(),
            validateEmailUseCase = get()
        )
    }

    viewModel {
        ConfirmEmailSignUpVIewModel(
            requestOtpGenerationUseCase = get(),
            confirmSignUpWithEmailUseCase = get()
        )
    }

    viewModel {
        LockScreenViewModel(
            authenticateWithPinUseCase = get(),
            checkIfBiometricsAvailableUseCase = get(),
            checkIfAppLockedWithBiometricsUseCase = get(),
            logoutUseCase = get(),
        )
    }

    viewModel {
        CreatePinViewModel(
            setupAppLockUseCase = get(),
            checkIfBiometricsAvailableUseCase = get(),
        )
    }

    viewModel {
        EnableBiometricsViewModel(
            setupAppLockedWithBiometricsUseCase = get(),
            checkIfBiometricsAvailableUseCase = get(),
        )
    }

    viewModel {
        TopUpScreenViewModel(
            getSuggestedTopUpValuesUseCase = get(),
            getCardByIdUseCase = get(),
            getDefaultCardUseCase = get(),
            topUpAccountUseCase = get(),
        )
    }

    viewModel {
        CardPickerViewModel(
            getAllCardsUseCase = get()
        )
    }

    viewModel {
        TransactionHistoryViewModel(
            getTransactionsUseCase = get(),
            observeTransactionStatusUseCase = get()
        )
    }

    viewModel {
        SendMoneyViewModel(
            getSuggestedSendValuesForCardBalance = get(),
            getCardByIdUseCase = get(),
            getDefaultCardUseCase = get(),
            getRecentContactUseCase = get(),
            getContactByIdUseCase = get(),
            sendMoneyUseCase = get(),
        )
    }

    viewModel {
        ContactPickerDialogViewModel(
            getContactsUseCase = get()
        )
    }

    viewModel {
        DisplayQrViewModel(
            generateQrCodeUseCase = get()
        )
    }

    viewModel {
        ScannedContactViewModel(
            loadUserFromQrCodeUseCase = get()
        )
    }

    viewModel {
        AppSettingsViewModel(
            checkIfBiometricsAvailableUseCase = get(),
            checkAppLockedWithBiometricsUseCase = get(),
            lockAppLockedWithBiometricsUseCase = get(),
        )
    }
}