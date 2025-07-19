package com.example.animespot.screens.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animespot.R
import com.example.animespot.api.Media
import com.example.animespot.screens.detail.DetailsViewModel
import com.example.animespot.screens.utils.stripHtml
import com.example.animespot.ui.theme.luckyfontfamily
import com.skydoves.landscapist.glide.GlideImage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel, detailsViewModel: DetailsViewModel, navigateToDetail: () -> Unit) {
    val animeList by homeViewModel.animes.observeAsState(emptyList())

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeScreenHeader()
            if (animeList.isEmpty()) {
                LoadingIndicator()
            } else {
                AnimeList(animeList, detailsViewModel, navigateToDetail)
            }
        }
    }
}

@Composable
fun HomeScreenHeader() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            painter = painterResource(id = R.drawable.animeimage),
            contentDescription = "Icon",
            modifier = Modifier.size(70.dp),
            alignment = Alignment.TopCenter
        )
    }
    Spacer(modifier = Modifier.padding(vertical = 1.dp))

    Text(
        text = stringResource(id = R.string.anime_rel),
        fontFamily = luckyfontfamily,
        fontSize = 20.sp
    )
}

@Composable
fun LoadingIndicator() {
    CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
}

@Composable
fun AnimeList(animeList: List<Media>, detailsViewModel: DetailsViewModel, navigateToDetail: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 56.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        items(animeList) { anime ->
            AnimeListItem(anime, detailsViewModel, navigateToDetail)
        }
    }
}

@Composable
fun AnimeListItem(anime: Media, detailsViewModel: DetailsViewModel, NavigateToDetail: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable {
                detailsViewModel.selectMedia(anime)


                detailsViewModel.checkIfFavorite(anime.id.toString())
                NavigateToDetail()
            }
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            GlideImage(
                imageModel = anime.coverImage?.large,
                contentDescription = "Anime cover image",
                modifier = Modifier
                    .size(110.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = anime.title?.userPreferred ?: "Unknown title",
                    fontWeight = FontWeight.Black,
                    fontFamily = luckyfontfamily,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stripHtml(anime.description ?: "No description"),
                    fontSize = 13.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
