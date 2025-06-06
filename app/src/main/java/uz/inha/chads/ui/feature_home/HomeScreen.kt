package uz.inha.chads.ui.feature_home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uz.inha.chads.R
import uz.inha.chads.ui.app_host.navigation.model.NavDestinations
import uz.inha.chads.ui.components.ScreenPreview
import uz.inha.chads.ui.components.ScreenSectionDivider
import uz.inha.chads.ui.components.decoration.SkeletonShape
import uz.inha.chads.ui.components.error.ErrorFullScreen
import uz.inha.chads.ui.components.header.ScreenHeader
import uz.inha.chads.ui.core.extensions.showToast
import uz.inha.chads.ui.core.resources.UiText
import uz.inha.chads.ui.feature_account.MoneyAmountUi
import uz.inha.chads.ui.feature_account.components.account_actions.AccountAction
import uz.inha.chads.ui.feature_cards.components.AddPaymentCardButton
import uz.inha.chads.ui.feature_cards.components.PaymentCard
import uz.inha.chads.ui.feature_cards.model.CardUi
import uz.inha.chads.ui.feature_home.components.AccountActionPanel
import uz.inha.chads.ui.feature_home.components.AccountActionPanel_Skeleton
import uz.inha.chads.ui.feature_home.model.HomeIntent
import uz.inha.chads.ui.feature_home.model.HomeState
import uz.inha.chads.ui.feature_profile.model.ProfileUi
import uz.inha.chads.ui.feature_savings.components.SavingCard
import uz.inha.chads.ui.feature_savings.model.SavingUi
import uz.inha.chads.ui.theme.primaryFontFamily
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onGoToDestination: (navEntry: NavDestinations) -> Unit = {},
    onAccountAction: (AccountAction) -> Unit = {},
    onCardDetails: (cardId: String) -> Unit = {},
    onSavingDetails: (id: Long) -> Unit = {}
) {

    LaunchedEffect(Unit) {
        viewModel.emitIntent(HomeIntent.EnterScreen)
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value
    when (state) {
        is HomeState.Success -> HomeScreen_Ui(
            state = state,
            onGoToDestination = onGoToDestination,
            onCardDetails = onCardDetails,
            onSavingDetails = onSavingDetails,
            onAccountAction = onAccountAction
        )

        is HomeState.Loading -> HomeScreen_Skeleton()
        is HomeState.Error -> ErrorFullScreen(error = state.error, onRetry = {
            viewModel.emitIntent(HomeIntent.EnterScreen)
        })
    }
}

@Composable
fun HomeScreen_Ui(
    state: HomeState.Success,
    onGoToDestination: (navEntry: NavDestinations) -> Unit = {},
    onCardDetails: (cardId: String) -> Unit = {},
    onSavingDetails: (id: Long) -> Unit = {},
    onAccountAction: (AccountAction) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(bottom = 24.dp)
    ) {

        ScreenHeader(
            toolbar = {
                HomeToolbar(
                    state.profile.fullName
                )
            }, panelVerticalOffset = 24.dp
        ) {
            AccountActionPanel(
                balanceFlow = state.balance
            ) {
                onAccountAction(it)
            }
        }

        Spacer(Modifier.height(8.dp))

        ScreenSectionDivider(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            title = UiText.StringResource(R.string.your_cards),
            onAction = {
                onGoToDestination.invoke(NavDestinations.RootGraph.CardList)
            })

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            if (state.cards.isNotEmpty()) {
                items(state.cards) { card ->
                    PaymentCard(cardUi = card, onCLick = { onCardDetails(card.id) })
                }

                if (state.cards.size < 2) {
                    item {
                        AddPaymentCardButton(onCLick = {
                            onGoToDestination.invoke(NavDestinations.RootGraph.AddCard)
                        })
                    }
                }

            } else {
                item {
                    AddPaymentCardButton(onCLick = {
                        onGoToDestination.invoke(NavDestinations.RootGraph.AddCard)
                    })
                }
            }
        }

        if (state.savings.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))

            ScreenSectionDivider(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                title = UiText.StringResource(R.string.your_saving),
                onAction = {
                    onGoToDestination.invoke(NavDestinations.RootGraph.SavingsList)
                })

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                state.savings.forEach {
                    SavingCard(
                        savingUi = it, onClick = onSavingDetails
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeToolbar(name: String) {
    val ctx = LocalContext.current

    TopAppBar(
        title = {
        Column(Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = stringResource(id = R.string.welcome_back), style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xB8FFFFFF),
                )
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = name, style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 26.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFFFFFFF),
                ), maxLines = 1, overflow = TextOverflow.Ellipsis
            )
        }
    },
        actions = {
            IconButton(onClick = {
                ctx.showToast("Not yet implemented")
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bell),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        modifier = Modifier
            .wrapContentHeight()
            .padding(top = 16.dp)
    )
}

@Composable
private fun HomeScreen_Skeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(bottom = 24.dp)
    ) {
        ScreenHeader(
            toolbar = {}, panelVerticalOffset = 24.dp
        ) {
            AccountActionPanel_Skeleton()
        }

        Spacer(Modifier.height(8.dp))

        ScreenSectionDivider(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            title = UiText.StringResource(R.string.your_cards),
            actionLabel = null
        )

        SkeletonShape(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .width(300.dp)
                .height(200.dp)
        )

        Spacer(Modifier.height(8.dp))

        ScreenSectionDivider(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            title = UiText.StringResource(R.string.your_saving),
            actionLabel = null
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            List(3) {
                SkeletonShape(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreen_Preview() {
    ScreenPreview {
        HomeScreen_Ui(HomeState.Success(profile = ProfileUi.mock(), cards = List(3) {
            CardUi.mock()
        }, savings = List(3) {
            SavingUi.mock()
        }))
    }
}


@Preview
@Composable
fun HomeScreen_Skeleton_Preview() {
    ScreenPreview {
        HomeScreen_Skeleton()
    }
}

@Preview
@Composable
fun HomeScreen_Empty() {
    ScreenPreview {
        HomeScreen_Ui(
            HomeState.Success(
                profile = ProfileUi.mock(),
                cards = emptyList(),
                savings = emptyList(),
                balance = flowOf(MoneyAmountUi("$2000"))
            ),
        )
    }
}