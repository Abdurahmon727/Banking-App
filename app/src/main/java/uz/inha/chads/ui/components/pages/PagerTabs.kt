package uz.inha.chads.ui.components.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.inha.chads.ui.theme.primaryFontFamily
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T>PagerTabRow(
    tabs: List<Page<T>>,
    pagerState: PagerState
) {
    require(pagerState.pageCount == tabs.size) {
        "Pages count should match tabs count"
    }

    val scope = rememberCoroutineScope()

//    SecondaryScrollableTabRow(
    SecondaryTabRow(
        selectedTabIndex = pagerState.currentPage,
        // TODO make indicator more close to design
        indicator = { tabPositions ->
            if (pagerState.currentPage < tabPositions.size) {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    height = 4.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        divider = @Composable {
            HorizontalDivider(
                modifier = Modifier.offset(y = (-1).dp),
                thickness = 2.dp,
                color = Color(0xFFF2F2F2)
            )
        },
//        edgePadding = 0.dp,
        modifier = Modifier.fillMaxWidth()
    ) {

        tabs.forEachIndexed { tabIndex, tab ->
            PagerTab(
                isSelected = pagerState.currentPage == tabIndex,
                text = tab.title,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(tabIndex)
                    }
                })
        }
    }

}


@Composable
fun PagerTab(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit = {}
) {
    Tab(
        selected = isSelected,
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {

        Box(
            modifier = Modifier.padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {

            if (isSelected) {
                Text(
                    text = text, style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                )
            } else {
                Text(
                    text = text, style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFCCCCCC),
                    )
                )
            }
        }
    }
}


