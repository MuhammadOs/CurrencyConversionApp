package com.example.currencyconversionapp.presentation.feature.conversion

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.currencyconversionapp.R
import com.example.currencyconversionapp.data.source.local.model.CurrencyEntity
import com.example.currencyconversionapp.presentation.components.ContentVisibility
import com.example.currencyconversionapp.presentation.components.CurrencyItem
import com.example.currencyconversionapp.presentation.components.EmptyView
import com.example.currencyconversionapp.presentation.components.Loading
import com.example.currencyconversionapp.presentation.components.NetworkError
import com.example.currencyconversionapp.presentation.feature.conversion.combosable.Converting
import com.example.currencyconversionapp.presentation.feature.favourites.FavouritesScreen
import com.example.currencyconversionapp.presentation.feature.favourites.FavouritesViewModel
import com.example.currencyconversionapp.presentation.theme.CurrencyConversionAppTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterScreen(
    viewModel: ConverterViewModel = hiltViewModel(),
    favViewModel: FavouritesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    var isSheetOpened by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = state){
        Log.d("ConverterScreen", "ConverterScreen: $state")
    }
    Loading(state = state.loadingVisibility())
    NetworkError(state = state.isError, error ="Network Error",)
    ContentVisibility(state =state.contentVisibility()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            item {
                Converting(viewModel, state)
            }
            item {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(MaterialTheme.colorScheme.secondary)
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.live_exchange_rates),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontWeight = FontWeight(600),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    OutlinedButton(
                        onClick = {
                            isSheetOpened = true
                        },
                        modifier = Modifier
                            .height(35.dp)
                    ) {
                        Image(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.add_icon),
                            contentDescription = "Add Icon",
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimary)
                        )
                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = stringResource(R.string.add_to_favorites),
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight(500),
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        )
                    }
                }
            }
            item {
                Text(
                    text = stringResource(id = R.string.my_portfolio),
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 24.sp,
                        fontWeight = FontWeight(400),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                )
            }
            if (state.favCurrencies.isEmpty()) {
                item {
                    EmptyView(state = true)
                }
            } else {
                items(items = state.favCurrencies) {
                    CurrencyItem(
                        currencyName = it.name,
                        flag = it.flagUrl,
                        rate = it.rate,
                        code=it.code,
                        loading=state.isFavoriteLoading,
                    )
                }
            }
        }
    }
    if (isSheetOpened) {
        ModalBottomSheet(
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            onDismissRequest = {
                isSheetOpened = false
                scope.launch {
                    favViewModel.getCurrencies()
                    viewModel.getAllFavCurrencies()
                }
            }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Image(
                    modifier = Modifier
                        .size(55.dp)
                        .aspectRatio(1f)
                        .align(Alignment.End)
                        .padding(16.dp)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                isSheetOpened = false
                            }),
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                )
                FavouritesScreen(
                    state.currencies
                )
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(0.3f)
                )
            }
        }
    }


}
@Preview(showBackground = true)
@Composable
fun PreviewFav() {
    CurrencyConversionAppTheme {
        ConverterScreen()
    }

}
