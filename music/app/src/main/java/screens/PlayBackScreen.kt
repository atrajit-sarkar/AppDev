package screens

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.music.ui.theme.MusicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PlayScreen() {
    MusicTheme {


        Scaffold { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                item {

                    val modifier = Modifier.padding(innerPadding)
                    Row(modifier = modifier) {
                        TopAppBar(
                            title = {
                                Row(modifier.fillMaxWidth()) {
                                    Icon(
                                        modifier = modifier.size(50.dp),
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Search",


                                        )
                                    Spacer(modifier = Modifier.width(90.dp))
//                        Box(modifier = Modifier.padding(vertical = 10.dp).background(color = MaterialTheme.colorScheme.primary).clip(RoundedCornerShape(30.dp))){
//
//
//                        }


                                    Card(
                                        modifier = Modifier
                                            .padding(vertical = 10.dp)
                                            .clip(RoundedCornerShape(30.dp))
                                    ) {
                                        Text(fontSize = 15.sp, text = "Song")

                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Card(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .padding(vertical = 10.dp)
                                    ) {
                                        Text(fontSize = 15.sp, text = "Lyric")

                                    }

                                }

                            },
                            modifier = modifier,
                            navigationIcon = {},
                            actions = {
                                Icon(
                                    modifier = modifier,
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "Search"
                                )

                            }
                        )


                    }
                    HorizontalDivider()
                    Card(modifier = Modifier.padding(26.dp).fillMaxWidth().height(400.dp).clip(RoundedCornerShape(50.dp)).background(color = MaterialTheme.colorScheme.secondary),elevation = CardDefaults.cardElevation(30.dp)){
                        Text(text = "",
                            modifier = Modifier.fillMaxSize(),
                            fontSize = 100.sp)

                    }
                    Row(modifier = Modifier.padding(20.dp).fillMaxWidth(),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween) {
                        Text(text = "song name", fontSize = 30.sp)

                        Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Search", modifier = Modifier.height(30.dp).width(30.dp).clickable {  })
                    }
                }


            }


        }
    }

}