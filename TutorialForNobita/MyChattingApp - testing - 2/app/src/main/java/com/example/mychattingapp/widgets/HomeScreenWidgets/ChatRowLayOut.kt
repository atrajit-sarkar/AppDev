package com.example.mychattingapp.widgets.HomeScreenWidgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatRow(
    contactName: String,
    recentMessage: String,
    messageSentTime: String,
    icon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier.size(50.dp)
        )
    },
    onClick: () -> Unit = {},
    onLongPress: () -> Unit = {},
    messageCounterEnabled: Boolean = true,
    messageCounterNumber: String = "1",
    messageDeliveryIcon: @Composable () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .height(69.dp)
            .combinedClickable(
                onLongClick = {
                    onLongPress()
                }
            ) {
                onClick()
            }
    ) {
        icon()
        Spacer(modifier = Modifier.width(20.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(200.dp)
                    .height(79.dp)
            ) {
                Text(
                    text = contactName,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (recentMessage != "") {

                        messageDeliveryIcon()
                        Spacer(modifier = Modifier.width(7.dp))

                        Text(
                            text = recentMessage,
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.LightGray,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(end = 8.dp)
            ) {

                if (recentMessage != "") {
                    Text(
                        text = messageSentTime,
                        color = if (messageCounterNumber == "0") Color.LightGray else Color.Green,
                        fontSize = 12.sp
                    )
                    if (messageCounterNumber != "0") {
                        Card(
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(Color.Green),
                            modifier = Modifier.size(23.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Green),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = messageCounterNumber,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.inversePrimary,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}