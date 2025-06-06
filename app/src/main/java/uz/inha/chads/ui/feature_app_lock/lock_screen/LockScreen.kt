package uz.inha.chads.ui.feature_app_lock.lock_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uz.inha.chads.R
import uz.inha.chads.domain.core.OperationResult
import uz.inha.chads.ui.app_host.host_utils.LocalScopedSnackbarState
import uz.inha.chads.ui.components.FullscreenProgressBar
import uz.inha.chads.ui.components.snackbar.SnackBarMode
import uz.inha.chads.ui.core.effects.OnResumeEffect
import uz.inha.chads.ui.core.error.asUiTextError
import uz.inha.chads.ui.core.extensions.findActivity
import uz.inha.chads.ui.feature_app_lock.components.AppLockScreen_Ui
import uz.inha.chads.ui.feature_app_lock.core.biometrics.BiometricAuthResult
import uz.inha.chads.ui.feature_app_lock.core.biometrics.BiometricsHelper
import uz.inha.chads.ui.feature_app_lock.core.biometrics.BiometricsIntent
import uz.inha.chads.ui.feature_logout.LogoutIntent
import uz.inha.chads.ui.feature_logout.LogoutDialog
import de.palm.composestateevents.EventEffect
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LockScreen(
    viewModel: LockScreenViewModel = koinViewModel(),
    onAppUnlock: () -> Unit = {},
    onLogoutSucceeded: () -> Unit = {}
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    val snackBarState = LocalScopedSnackbarState.current


    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.emitLogoutIntent(LogoutIntent.ToggleLogoutDialog(isShown = true))
                        }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.ic_logout),
                            stringResource(id = R.string.log_out),
                            modifier = Modifier.size(28.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) {
        AppLockScreen_Ui(
            state = state.uiState,
            onIntent = {
                viewModel.emitAppLockIntent(it)
            },
        )

        if (state.logoutState.isLoading) {
            FullscreenProgressBar()
        }

        if (state.logoutState.showLogoutDialog) {
            LogoutDialog(
                onDismiss = {
                    viewModel.emitLogoutIntent(LogoutIntent.ToggleLogoutDialog(isShown = false))
                },
                onConfirmLogout = {
                    viewModel.emitLogoutIntent(LogoutIntent.ConfirmLogOut)
                }
            )
        }
    }

    EventEffect(
        event = state.unlockWithPinEvent,
        onConsumed = viewModel::consumeUnlockWithPinEvent
    ) {
        onAppUnlock()
    }

    EventEffect(
        event = state.showBiometricsPromptEvent,
        onConsumed = viewModel::consumeShowBiometricsPromptEvent
    ) {
        val activity = context.findActivity()
        activity?.let {
            BiometricsHelper.showPrompt(
                activity = activity,
                prompt = state.biometricsPromptState,
                onError = {
                    viewModel.emitBiometricsIntent(
                        BiometricsIntent.ConsumeAuthResult(
                            BiometricAuthResult.Failure(it)
                        )
                    )
                },
                onSuccess = {
                    viewModel.emitBiometricsIntent(
                        BiometricsIntent.ConsumeAuthResult(
                            BiometricAuthResult.Success
                        )
                    )
                }
            )
        }
    }

    EventEffect(
        event = state.unlockWithBiometricsResultEvent,
        onConsumed = viewModel::consumeBiometricAuthEvent,
    ) { biometricsRes ->
        when (biometricsRes) {
            is BiometricAuthResult.Success -> {
                onAppUnlock()
            }

            is BiometricAuthResult.Failure -> {
                snackBarState.show(biometricsRes.error, SnackBarMode.Negative)
            }
        }
    }

    OnResumeEffect {
        viewModel.emitBiometricsIntent(
            BiometricsIntent.RefreshBiometricsAvailability
        )
    }

    EventEffect(
        event = state.logoutState.logoutEvent,
        onConsumed = viewModel::consumeLogoutEvent,
    ) { logoutResult ->

        when (logoutResult) {
            is OperationResult.Failure -> {
                snackBarState.show(
                    message = logoutResult.error.errorType.asUiTextError().asString(context),
                    snackBarMode = SnackBarMode.Negative
                )
            }

            is OperationResult.Success -> {
                onLogoutSucceeded()
            }
        }
    }
}