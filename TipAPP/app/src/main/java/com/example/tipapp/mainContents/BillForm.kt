package com.example.tipapp.mainContents

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.tipapp.components.InputField
import com.example.tipapp.utils.calculateTotalPerPerson
import com.example.tipapp.utils.calculateTotalTip
import com.example.tipapp.widgets.RoundIconButton

@SuppressLint("DefaultLocale")
@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    onValChange: (String) -> Unit = {}
) {
    val totalBillState = remember {
        mutableStateOf("")
    }

    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    val noOfPeople = remember {
        mutableIntStateOf(1)
    }

    val sliderPositionState = remember {
        mutableFloatStateOf(0f)
    }

    val percentage = ((sliderPositionState.floatValue) * 100).toInt()

    val slidingFinished = remember {
        mutableStateOf(false)
    }
    val range = IntRange(start = 1, endInclusive = 100)

    val tipAmountState = remember {
        mutableDoubleStateOf(0.0)
    }
    val totalPerPersonState = remember {
        mutableDoubleStateOf(0.0)
    }

    TopHeader(totalPerPerson = totalPerPersonState.doubleValue)
    Spacer(modifier = Modifier.height(10.dp))

    Surface(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            InputField(
                modifier = Modifier.fillMaxWidth(),
                valueState = totalBillState,
                labelId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    onValChange(totalBillState.value.trim())

                    keyboardController?.hide()

                }
            )
            if (validState) {
                Row(modifier = Modifier.padding(3.dp), horizontalArrangement = Arrangement.Start) {
                    Text(
                        "Split",
                        modifier = Modifier.align(
                            alignment = Alignment.CenterVertically
                        )
                    )
                    Spacer(modifier = Modifier.width(90.dp))
                    Row(
                        modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        RoundIconButton(
                            imageVector = Icons.Default.Remove,
                            onClick = {
                                if (noOfPeople.intValue > 1) {

                                    noOfPeople.intValue--
                                }
                                totalPerPersonState.doubleValue = calculateTotalPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    splitBy = noOfPeople.intValue,
                                    tipPercentage = percentage
                                )
                            }
                        )
                        Text(
                            text = "${noOfPeople.intValue}",
                            modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 9.dp, end = 9.dp)
                        )
                        RoundIconButton(imageVector = Icons.Default.Add,
                            onClick = {
                                if (noOfPeople.intValue < range.last) {

                                    noOfPeople.intValue++
                                }
                                totalPerPersonState.doubleValue = calculateTotalPerPerson(
                                    totalBill = totalBillState.value.toDouble(),
                                    splitBy = noOfPeople.intValue,
                                    tipPercentage = percentage
                                )
                            })
                    }

                }

                Spacer(modifier = Modifier.height(20.dp))
                //Tip Row
                Row(modifier = Modifier.padding(3.dp), horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = "Tip",
                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )

                    Spacer(modifier = Modifier.width(130.dp))

                    Text(
                        text = "$ ${tipAmountState.doubleValue}",
                        modifier = Modifier.align(alignment = Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(200.dp))
                }

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "$percentage %")

                    //Slider
                    Slider(value = sliderPositionState.floatValue, onValueChange = { newVal ->
                        sliderPositionState.floatValue = newVal
                        tipAmountState.doubleValue = calculateTotalTip(
                            totalBill = totalBillState.value.toDouble(),
                            percentage = ((newVal) * 100).toInt()
                        )

                        totalPerPersonState.doubleValue = calculateTotalPerPerson(
                            totalBill = totalBillState.value.toDouble(),
                            splitBy = noOfPeople.intValue,
                            tipPercentage = ((newVal) * 100).toInt()
                        )
                        slidingFinished.value = false

                    },
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                        steps = 5,
                        onValueChangeFinished = {
                            slidingFinished.value = true
                        })
                    if (slidingFinished.value) {
                        Text(text = "You choose $percentage %.Great Choice!")
                    }
                }

            } else {
                Box() {}
            }

        }
    }

}