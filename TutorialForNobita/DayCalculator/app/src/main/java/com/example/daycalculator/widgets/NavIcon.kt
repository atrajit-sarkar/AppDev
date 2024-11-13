package com.example.daycalculator.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.guru.fontawesomecomposelib.FaIcon
import com.guru.fontawesomecomposelib.FaIconType
import com.guru.fontawesomecomposelib.FaIcons

@Composable
fun NavIcon(
    caption: String,
    icon: ImageVector?=null,
    faIconSolidIcon: FaIconType.SolidIcon?=null,
    faIconRegularIcon: FaIconType.RegularIcon?=null,
    faIconBrandIcon: FaIconType.BrandIcon?=null,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        IconButton(onClick = {
            onClick()

        }) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
            } else if ( faIconSolidIcon!=null){
                FaIcon(
                    faIcon = faIconSolidIcon,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            } else if(faIconBrandIcon!=null){
                FaIcon(
                    faIcon = faIconBrandIcon,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            } else if(faIconRegularIcon!=null){
                FaIcon(
                    faIcon = faIconRegularIcon,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        Text(text = caption)
    }
}