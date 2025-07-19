package com.example.animespot.screens.search


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animespot.api.Media
import com.example.animespot.screens.utils.stripHtml
import com.example.animespot.ui.theme.Orange
import com.example.animespot.ui.theme.luckyfontfamily
import com.skydoves.landscapist.glide.GlideImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(searchViewModel: SearchViewModel ) {
    val mediaList by searchViewModel.mediaList.collectAsState()
    val isLoading by searchViewModel.isLoading.collectAsState()
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }




    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(bottom = 30.dp),
            verticalArrangement = Arrangement.Top
        )
        {
            SearchBar(
                query = text,
                onQueryChange = { text = it },
                onSearch = {
                    searchViewModel.searchAnime(text)
                    active = false
                },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text(text = "Search an anime") },
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "search")
                },
                trailingIcon = {
                    if (active) {
                        Icon(
                            modifier = Modifier.clickable {
                                if (text.isNotEmpty()) {
                                    text = ""
                                } else {
                                    active = false
                                }
                            },
                            imageVector = Icons.Filled.Close, contentDescription = "Close Icon"
                        )
                    }
                },

                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            {
            }
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp)
                )
            } else if (text.isNotEmpty()) {
                mediaList?.let {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 80.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(it) { anime ->
                            AnItem(anime)
                        }
                    }
                }
            }
        }
    }
}

    @Composable
    fun AnItem(anime: Media) {
            GlideImage(
                imageModel = anime.coverImage?.extraLarge,
                contentDescription = "Anime cover image",
                modifier = Modifier
                    .size(400.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = anime.title?.userPreferred ?: "Unknown title",
                        fontFamily = luckyfontfamily,
                        fontSize = 20.sp,
                        maxLines = 1,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Spacer(modifier = Modifier.width(190.dp))

                    ScoreCircle(score = anime.averageScore)
                }

                Text(
                    text = stripHtml(anime.description ?: "No description"),
                    fontSize = 18.sp,
                    maxLines = 6,
                )

                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Genre : ${anime.genres ?: "No genre"}",
                    fontFamily = luckyfontfamily,
                    fontSize = 20.sp,
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Year : ${anime.seasonYear ?: "No season Year"}",
                    fontFamily = luckyfontfamily,
                    fontSize = 20.sp,
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row(modifier = Modifier.fillMaxWidth()) {

                    Text(
                        text = "Episode : ${anime.episodes?.toString() ?: "??"}",
                        fontFamily = luckyfontfamily,
                        fontSize = 20.sp,
                        maxLines = 6,

                        )
                    Spacer(modifier = Modifier.width(160.dp))
                    Text(
                        text = "Status : ${anime.status ?: "No status"}",
                        fontFamily = luckyfontfamily,
                        fontSize = 20.sp,
                        maxLines = 6,
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
@Composable
fun ScoreCircle(score: Int?, textColor: Color = Color.White) {

    val backgroundColor = when {
        score == null -> Color.Gray
        score < 50 -> Color.Red
        score in 50..69 -> Orange
        else -> Color.Green
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(backgroundColor)
    ) {
        Text(
            text = score?.toString() ?: "-",
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}