package com.example.mychattingapp.ChatsData

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIcons

data class ChannelsData(
    var channelname: String,
    var channelicon: @Composable () -> Unit
)

fun loadChannels(): List<ChannelsData> {
    return listOf<ChannelsData>(
        ChannelsData(channelname = "123",
            channelicon = {
                FaIcon(
                    faIcon = FaIcons.UserCircle,
                    size = 80.dp

                )

            }),
        ChannelsData(channelname = "123",
            channelicon = {
                FaIcon(
                    faIcon = FaIcons.UserCircle,
                    size = 80.dp

                )

            }),
        ChannelsData(channelname = "123",
            channelicon = {
                FaIcon(
                    faIcon = FaIcons.UserCircle,
                    size = 80.dp

                )

            }),
        ChannelsData(channelname = "123",
            channelicon = {
                FaIcon(
                    faIcon = FaIcons.UserCircle,
                    size = 80.dp

                )

            })
    )
}