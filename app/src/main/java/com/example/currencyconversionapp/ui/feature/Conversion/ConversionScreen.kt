package com.example.currencyconversionapp.ui.feature.Conversion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconversionapp.ui.composables.AmountField
import com.example.currencyconversionapp.ui.composables.CustomButton
import com.example.currencyconversionapp.ui.composables.SpinnerComponent

@Preview(showBackground = true, device = Devices.PIXEL_3)
@Composable
fun ConversionScreen() {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Amount",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(600),
                        color = Color.Black,
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                AmountField(121.dp, 48.dp)
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "From",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                SpinnerComponent(184.dp, 48.dp)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "To",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(600),
                        color = Color.Black,
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                SpinnerComponent(184.dp, 48.dp)
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Amount",
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF000000),
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                AmountField(121.dp, 48.dp)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        CustomButton("Convert")
    }
}