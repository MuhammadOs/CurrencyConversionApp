package com.example.currencyconversionapp.ui.feature.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconversionapp.R
import com.example.currencyconversionapp.ui.feature.comparison.ComparisonScreen
import com.example.currencyconversionapp.ui.feature.conversion.ConverterScreen
import com.example.currencyconversionapp.ui.theme.Black
import com.example.currencyconversionapp.ui.theme.ButtonColor
import com.example.currencyconversionapp.ui.theme.CurrencyConversionAppTheme
import com.example.currencyconversionapp.ui.theme.Grey
import com.example.currencyconversionapp.ui.theme.White
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    val pagerState = rememberPagerState(pageCount = {
        2
    }, initialPage = 0)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(Modifier.weight(0.3f)) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .paint(
                        painter = painterResource(id = R.drawable.background),
                        contentScale = ContentScale.Crop
                    )
                    .paint(
                        painter = painterResource(id = R.drawable.grad),
                        contentScale = ContentScale.Crop
                    )
                    .padding(start = 16.dp, end = 16.dp, top = 50.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = 26.sp,
                        fontWeight = FontWeight(700),
                        color = Color.White,
                    )
                )
                Text(
                    text = stringResource(id = R.string.currency_convert),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 42.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_bold)),
                        fontWeight = FontWeight(600),
                        color = Color.White,
                    )
                )
                Text(
                    text = stringResource(id = R.string.check_live_exchange_rates),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily= FontFamily(Font(R.font.montserrat_regular)),
                        fontWeight = FontWeight(400),
                        color = Color.White,
                    )
                )
            }
            centerControls(
                Modifier
                    .align(Alignment.BottomCenter)
                    .layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(placeable.width, placeable.height) {
                            placeable.place(0, placeable.height / 2)
                        }
                    }, pagerState
            )
        }
        bottomControls(Modifier.weight(0.7f), pagerState)
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun centerControls(layout: Modifier, pagerState: PagerState) {
    CustomTabSample(layout, pagerState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun bottomControls(layout: Modifier, pagerState: PagerState) {
    Column(
        modifier = layout.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = layout
                .fillMaxSize()
        ) { page ->
            when (page) {
                0 -> {
                    ConverterScreen()
                }

                1 -> {
                    ComparisonScreen()
                }
            }
        }
    }
}


@Composable
private fun MyTabIndicator(
    indicatorWidth: Dp,
    indicatorOffset: Dp,
    indicatorColor: Color,
    paddingStart: Dp = 0.dp,
    paddingEnd: Dp = 0.dp
) {
    Box(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .padding(start = paddingStart, end = paddingEnd)
            .fillMaxHeight()
            .width(indicatorWidth)
            .offset(x = indicatorOffset)
            .clip(CircleShape)
            .background(color = indicatorColor)

    )
}


@Composable
private fun MyTabItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onClick: () -> Unit,
    tabWidth: Dp,
    text: String,
) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            Black
        } else {
            ButtonColor
        },
        animationSpec = tween(easing = LinearEasing), label = "",
    )

    val interactionSource = remember { MutableInteractionSource() }

    Text(
        modifier = modifier
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick()
            }
            .width(tabWidth)
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp,
            )
            .fillMaxHeight()
            .wrapContentHeight(Alignment.CenterVertically),
        text = text,
        fontFamily = FontFamily(Font(R.font.poppins_regular)),
        fontWeight = FontWeight(400),
        color = tabTextColor,
        textAlign = TextAlign.Center,
    )
}


@Composable
fun CustomTab(
    selectedItemIndex: Int,
    items: List<String>,
    modifier: Modifier = Modifier,
    tabWidth: Dp = 140.dp,
    onClick: (index: Int) -> Unit,
) {
    val indicatorOffset: Dp by animateDpAsState(
        targetValue = tabWidth * selectedItemIndex,
        animationSpec = tween(easing = LinearEasing), label = "",
    )

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Grey)
            .height(54.dp)
            .padding(
                end = if (selectedItemIndex == 1) 4.dp else 0.dp,
                start = if (selectedItemIndex == 0) 4.dp else 0.dp
            )
    ) {
        MyTabIndicator(
            indicatorWidth = tabWidth,
            indicatorOffset = indicatorOffset,
            indicatorColor = Color.White,
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.clip(CircleShape),
        ) {
            items.mapIndexed { index, text ->
                val isSelected = index == selectedItemIndex
                MyTabItem(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    isSelected = isSelected,
                    onClick = {
                        onClick(index)
                    },
                    tabWidth = tabWidth,
                    text = text,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomTabSample(layout: Modifier, pagerState: PagerState) {
    val (selected, setSelected) = remember {
        mutableStateOf(0)
    }
    val coroutineScope = rememberCoroutineScope()
    CustomTab(
        modifier = layout,
        items = listOf(
            stringResource(id = R.string.convert),
            stringResource(id = R.string.compare)
        ),
        selectedItemIndex = selected,
        onClick = {
            coroutineScope.launch {
                pagerState.animateScrollToPage(it)
            }
            setSelected(it)
        },
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun PreviewFavs() {
    CurrencyConversionAppTheme {
        HomeScreen()
    }
}
