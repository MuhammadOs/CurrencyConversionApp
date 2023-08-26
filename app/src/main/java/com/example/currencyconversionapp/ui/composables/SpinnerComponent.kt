package com.example.currencyconversionapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.currencyconversionapp.R
import com.example.currencyconversionapp.ui.theme.FieldColor
import com.example.currencyconversionapp.ui.theme.FieldShadowColor

/* This is a dummy list of some currencies to test the DropDownMenu */
data class Currency(val currencyCode: String, val currencyName: String, val currencyFlag: String)

val currenciesList = listOf(
    Currency(
        currencyCode = "EGP",
        currencyName = "Egyptian Pound",
        currencyFlag = "https://cdn.britannica.com/85/185-004-1EA59040/Flag-Egypt.jpg"
    ),
    Currency(
        currencyCode = "USD",
        currencyName = "US Dollar",
        currencyFlag = "https://cdn.britannica.com/79/4479-050-6EF87027/flag-Stars-and-Stripes-May-1-1795.jpg"
    ),
    Currency(
        currencyCode = "EUR",
        currencyName = "Euro",
        currencyFlag = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Flag_of_Europe.svg/2560px-Flag_of_Europe.svg.png"
    ),
    Currency(
        currencyCode = "GBP",
        currencyName = "Sterling Pound",
        currencyFlag = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Flag_of_the_United_Kingdom_%283-5%29.svg/1280px-Flag_of_the_United_Kingdom_%283-5%29.svg.png"
    ),
    Currency(
        currencyCode = "AED",
        currencyName = "UAE Dirham",
        currencyFlag = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/cb/Flag_of_the_United_Arab_Emirates.svg/1280px-Flag_of_the_United_Arab_Emirates.svg.png"
    ),
    Currency(
        currencyCode = "JPY",
        currencyName = "Japan Yen",
        currencyFlag = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Flag_of_Japan.svg/1280px-Flag_of_Japan.svg.png"
    ),
    Currency(
        currencyCode = "SAR",
        currencyName = "Saudi Riyal",
        currencyFlag = "https://cdn.britannica.com/79/5779-004-DC479508/Flag-Saudi-Arabia.jpg"
    ),
    Currency(
        currencyCode = "KWD",
        currencyName = "Kuwait Dinar",
        currencyFlag = "https://cdn.britannica.com/70/5770-004-A99DD01D/Flag-Kuwait.jpg"
    )
)


/** The SpinnerComponent is a component where the users can select the currency they want to convert from,
 * it's a dropdown menu that have all the currencies and the user should choose one to convert it to another
 * currency.
 */
@Preview
@Composable
fun SpinnerComponent() {
    // this is a mutable state variable to control the dropDown menu whether it's expanded or not

    var isExpanded by remember { mutableStateOf(false) }

    // this the selected currency that the user will select from the dropDown menu, it's mutableState so it recompose the component every time the user select another currency

    var selectedCurrencyCode by remember { mutableStateOf("EGP") }
    var selectedCurrencyFlag by remember { mutableStateOf("https://cdn.britannica.com/85/185-004-1EA59040/Flag-Egypt.jpg") }

    // The container of the flag, currency name and the drop icon
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = FieldShadowColor,
                shape = RoundedCornerShape(size = 20.dp)
            )
            .padding(0.5.dp)
            .fillMaxWidth()
            .height(48.dp)
            .background(color = FieldColor, shape = RoundedCornerShape(size = 20.dp))
            .clickable { isExpanded = isExpanded.not() }
    ) {
        // The flag of the currency
        AsyncImage(
            model = selectedCurrencyFlag,
            contentDescription = "currency flag",
            placeholder = painterResource(id = R.drawable.placeholder),
            error = painterResource(id = R.drawable.placeholder),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .padding(start = 11.dp)
                .width(28.dp)
                .height(20.dp)
        )

        // The currency name
        Text(
            text = selectedCurrencyCode,
            style = TextStyle(
                fontSize = 16.sp,
                /*fontFamily = FontFamily(Font(R.font.open sans)),*/
                fontWeight = FontWeight(400),
                color = MaterialTheme.colorScheme.onPrimary,
            ),
            modifier = Modifier
                .weight(0.8f)
                .padding(start = 8.dp)
        )

        // The drop icon that shows list of all the currencies the user can choose from
        IconButton(
            onClick = { isExpanded = isExpanded.not() },
            modifier = Modifier
                .padding(end = 16.dp)
                .size(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.drop_icon),
                contentDescription = "Show all currencies",
                modifier = Modifier
                    .padding(1.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        // The menu of all the currencies where the user can choose only one from it
        DropdownMenu(
            expanded = isExpanded,
            modifier = Modifier
                .height(250.dp)
                .background(color = FieldColor),
            onDismissRequest = { isExpanded = isExpanded.not() },
            offset = DpOffset(15.dp, 0.dp)
        ) {
            repeat(currenciesList.size) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = currenciesList[it].currencyCode,
                            style = TextStyle(
                                fontSize = 16.sp,
                                /*fontFamily = FontFamily(Font(R.font.open sans)),*/
                                fontWeight = FontWeight(400),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    },
                    leadingIcon = {
                        AsyncImage(
                            model = currenciesList[it].currencyFlag,
                            placeholder = painterResource(id = R.drawable.placeholder),
                            error = painterResource(id = R.drawable.placeholder),
                            contentDescription = "Currency Flag",
                            modifier = Modifier
                                .width(28.dp)
                                .height(20.dp),
                            contentScale = ContentScale.FillBounds
                        )
                    },
                    onClick = {
                        selectedCurrencyCode = currenciesList[it].currencyCode
                        selectedCurrencyFlag = currenciesList[it].currencyFlag
                        isExpanded = isExpanded.not()
                    }
                )
            }
        }
    }
}


