package uz.inha.chads.ui.feature_account.action_topup

import android.os.Build
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uz.inha.chads.R
import uz.inha.chads.domain.features.account.model.MoneyAmount
import uz.inha.chads.ui.app_host.host_utils.LocalScopedSnackbarState
import uz.inha.chads.ui.components.FullscreenProgressBar
import uz.inha.chads.ui.components.PrimaryButton
import uz.inha.chads.ui.components.ScreenPreview
import uz.inha.chads.ui.components.SecondaryToolBar
import uz.inha.chads.ui.components.TextBtn
import uz.inha.chads.ui.components.dialogs.SuccessDialog
import uz.inha.chads.ui.components.header.ScreenHeader
import uz.inha.chads.ui.components.snackbar.SnackBarMode
import uz.inha.chads.ui.core.effects.EnterScreenEffect
import uz.inha.chads.ui.core.error.asUiTextError
import uz.inha.chads.ui.core.extensions.showToast
import uz.inha.chads.ui.core.permissions.AskPermissionResult
import uz.inha.chads.ui.core.permissions.CheckPermissionResult
import uz.inha.chads.ui.core.permissions.LocalPermissionHelper
import uz.inha.chads.ui.core.resources.UiText
import uz.inha.chads.ui.feature_account.AmountPickersState
import uz.inha.chads.ui.feature_account.components.BalanceGridPicker
import uz.inha.chads.ui.feature_account.components.BalanceSliderPicker
import uz.inha.chads.ui.feature_cards.components.PanelCardPicker
import uz.inha.chads.ui.feature_cards.dialog_card_picker.CardPickerDialog
import uz.inha.chads.ui.theme.primaryFontFamily
import de.palm.composestateevents.EventEffect
import de.palm.composestateevents.NavigationEventEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun TopUpScreen(
    viewModel: TopUpScreenViewModel = koinViewModel(),
    selectedCardId: String? = null,
    onBack: () -> Unit
) {
    val snackbarHostState = LocalScopedSnackbarState.current
    val context = LocalContext.current
    val permissionHelper = LocalPermissionHelper.current

    val state = viewModel.state.collectAsStateWithLifecycle().value
    TopUpScreen_Ui(
        state = state,
        onIntent = { viewModel.emitIntent(it) },
        onBack = onBack
    )

    EventEffect(
        event = state.cardPickerState.cardSelectErrorEvent,
        onConsumed = viewModel::consumeLoadCardErrorEvent
    ) {
        snackbarHostState.show(it.asUiTextError().asString(context), SnackBarMode.Negative)
    }

    NavigationEventEffect(
        event = state.requiredBackNavEvent,
        onConsumed = viewModel::consumeBackNavEvent
    ) {
        onBack()
    }

    EnterScreenEffect {
        viewModel.emitIntent(TopUpScreenIntent.EnterScreen(selectedCardId = selectedCardId))
    }

    EnterScreenEffect {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationPermission = android.Manifest.permission.POST_NOTIFICATIONS

            when (permissionHelper.checkIfPermissionGranted(context, notificationPermission)) {
                CheckPermissionResult.SHOULD_ASK_PERMISSION -> {
                    permissionHelper.askForPermission(context, notificationPermission) { res ->
                        when (res) {
                            AskPermissionResult.GRANTED -> {
                                context.showToast(R.string.permission_granted)
                            }

                            AskPermissionResult.REJECTED -> {
                                context.showToast(R.string.permission_rejected)
                            }
                        }
                    }
                }

                // DO nothing as permission is not mandatory for user flow
                CheckPermissionResult.SHOULD_REDIRECT_TO_SETTINGS -> {}
                CheckPermissionResult.PERMISSION_ALREADY_GRANTED -> {}
            }
        }
    }
}

@Composable
private fun TopUpScreen_Ui(
    state: TopUpScreenState,
    onIntent: (TopUpScreenIntent) -> Unit = {},
    onBack: () -> Unit = {}
) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .height(maxHeight)
                .width(maxWidth)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenHeader(toolbar = {
                SecondaryToolBar(
                    onBack = { onBack() },
                    title = UiText.StringResource(R.string.top_up),
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(top = 16.dp)
                )
            }) {
                PanelCardPicker(
                    selectedCard = state.cardPickerState.selectedCard,
                    isLoading = state.cardPickerState.isLoading,
                    onCardPickerClick = {
                        onIntent(TopUpScreenIntent.ToggleCardPicker(show = true))
                    })
            }

            Spacer(Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.enter_nominal), style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary,
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )

            Spacer(Modifier.height(16.dp))

            BalanceSliderPicker(
                modifier = Modifier.padding(horizontal = 24.dp),
                selectedValue = state.amountState.selectedAmount,
                onValueSelected = {
                    onIntent(TopUpScreenIntent.UpdateSelectedValue(it))
                })

            Spacer(Modifier.height(24.dp))

            BalanceGridPicker(
                proposedValues = state.amountState.proposedValues, selectedValue = state.amountState.selectedAmount, onValueSelected = {
                    onIntent(TopUpScreenIntent.UpdateSelectedValue(it))
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )

            Spacer(Modifier.weight(1f))
            Spacer(Modifier.height(36.dp))

            PrimaryButton(
                onClick = { onIntent(TopUpScreenIntent.ProceedClick) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                text = stringResource(R.string.proceed),
                isEnabled = state.proceedButtonEnabled
            )
            TextBtn(
                onClick = { onBack() },
                modifier = Modifier
                    .wrapContentSize()
                    .padding(vertical = 8.dp),
                text = stringResource(id = R.string.cancel)
            )
        }
    }

    if (state.isLoading) {
        FullscreenProgressBar()
    }

    if (state.cardPickerState.showCardPicker) {
        CardPickerDialog(
            onDismissRequest = { selectedCardId ->
                onIntent(TopUpScreenIntent.ToggleCardPicker(show = false))
                selectedCardId?.let {
                    onIntent(TopUpScreenIntent.ChooseCard(selectedCardId))
                }
            },
            defaultSelectedCard = state.cardPickerState.selectedCard?.id
        )
    }

    if (state.showSuccessDialog) {
        SuccessDialog(
            title = UiText.StringResource(R.string.top_up_successfully),
            message = UiText.StringResource(R.string.topup_exaplanation),
            onDismiss = {
                onIntent(TopUpScreenIntent.DismissSuccessDialog)
            }
        )
    }
}

@Composable
@Preview
fun TopUpScreen_Preview() {
    ScreenPreview {
        val amounts = setOf(100, 200, 300, 400, 500, 600).map {
            MoneyAmount(it.toFloat())
        }.toSet()

        TopUpScreen_Ui(
            state = TopUpScreenState(
                amountState = AmountPickersState(
                    selectedAmount = MoneyAmount(100f), proposedValues = amounts
                )
            )
        )
    }
}