package screens

import android.media.Image
import android.provider.CalendarContract
import android.provider.CalendarContract.Colors
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.music.ui.theme.MusicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreen() {
    MaterialTheme() {

    Scaffold {
        LazyColumn(modifier = Modifier.padding(it)) {
            item(){
            TopAppBar(
                title = {
                    Text(text = "MUSIC",
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.displayLarge)
                },
                modifier = Modifier.padding(it)
                    .fillMaxWidth(),
                navigationIcon = {

                },
                actions = {
                    Image(
                        modifier = Modifier.clickable { },
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        modifier = Modifier.clickable { },
                        imageVector = Icons.Default.Email,
                        contentDescription = "Search"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        modifier = Modifier.clickable { },
                        imageVector = Icons.Default.Person,
                        contentDescription = "Search"
                    )

                },

                )
                Card(modifier = Modifier.padding(16.dp).fillMaxWidth(),colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
                Text(fontSize =30.sp, text = " Playlists")
            }
                Card(modifier = Modifier.padding(16.dp).fillMaxWidth(),colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
                Text(fontSize =30.sp, text = " Artists")
            }
                Card(modifier = Modifier.padding(16.dp).fillMaxWidth(),colors = CardDefaults.cardColors(containerColor = Color.Transparent)) {
                Text(fontSize =30.sp, text = " Albums")
            }
                HorizontalDivider()
                Card (modifier = Modifier.fillMaxWidth().padding(5.dp),colors = CardDefaults.cardColors(containerColor = Color.Transparent)){
                    Text(fontSize = 15.sp, text = "All Songs")
                }
                Box(modifier = Modifier.padding(16.dp)){
                    
                }

            }


        }




        }

    }
    }

