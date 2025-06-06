package uz.inha.chads.ui.feature_home.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uz.inha.chads.R
import uz.inha.chads.ui.components.cards.PrimaryCard
import uz.inha.chads.ui.components.decoration.SkeletonShape
import uz.inha.chads.ui.feature_account.MoneyAmountUi
import uz.inha.chads.ui.feature_account.components.account_actions.AccountAction
import uz.inha.chads.ui.feature_account.components.account_actions.AccountActionRow
import uz.inha.chads.ui.theme.primaryFontFamily
import kotlinx.coroutines.flow.Flow

@Composable
fun AccountActionPanel(
    balanceFlow: Flow<MoneyAmountUi?>,
    onActionClick: (action: AccountAction) -> Unit
) {
    val balance = balanceFlow.collectAsStateWithLifecycle(initialValue = null).value

    PrimaryCard {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.my_balance),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFF05324),
                    )
                )

                Spacer(Modifier.weight(1f))

                if (balance != null) {
                    Text(
                        text = balance.amountStr,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFFF05324),
                        )
                    )
                } else {
                    SkeletonShape(
                        modifier = Modifier
                            .width(100.dp)
                            .height(18.dp),
                        shape = RoundedCornerShape(4.dp)
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                thickness = 2.dp,
                color = Color(0xFFF2F2F2)
            )


            BoxWithConstraints(Modifier.fillMaxWidth()) {
                AccountActionRow(
                    modifier = Modifier.width(maxWidth),
                    paddingValues = PaddingValues(
                        top = 16.dp,
                        bottom = 8.dp,
                        start = 12.dp,
                        end = 12.dp
                    ),
                    onActionClick = onActionClick
                )
            }
        }

    }

}

@Composable
fun AccountActionPanel_Skeleton() {

    PrimaryCard {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.my_balance),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.primary,
                    )
                )

                Spacer(Modifier.weight(1f))

                SkeletonShape(
                    modifier = Modifier
                        .width(100.dp)
                        .height(18.dp),
                    shape = RoundedCornerShape(4.dp)
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                thickness = 2.dp,
                color = Color(0xFFF2F2F2)
            )

            SkeletonShape(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp, start = 12.dp, end = 12.dp)
                    .height(100.dp),
                shape = RoundedCornerShape(4.dp)
            )
        }
    }
}