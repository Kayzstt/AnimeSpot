package com.example.animespot.screens.detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animespot.R
import com.example.animespot.api.Media
import com.example.animespot.repository.UserRepository
import com.example.animespot.screens.utils.formatTimestamp
import com.example.animespot.screens.utils.stripHtml
import com.example.animespot.ui.theme.Orange
import com.example.animespot.ui.theme.luckyfontfamily
import com.skydoves.landscapist.glide.GlideImage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailScreen(detailsViewModel: DetailsViewModel,userRepository: UserRepository,navigateToBack:() -> Unit) {
    val context = LocalContext.current
    val media = detailsViewModel.selectedMedia.observeAsState().value
    val trailerUrl = detailsViewModel.fullTrailerUrl.observeAsState().value
    val isAlreadyFavorite by detailsViewModel.isFavorite.observeAsState()


    if (media != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                MediaDetails(media)
                Spacer(modifier = Modifier.height(16.dp))
                if (media.trailer != null && trailerUrl != null) {
                    TrailerButton(trailerUrl, context)
                }
                if(isAlreadyFavorite!= false){
                    Text(text = "Already added in favorite")
                }else{
                    AddToFavoritesButton(detailsViewModel,userRepository,navigateToBack)
                }

            }
        }

            }
        }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MediaDetails(media: Media) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = media.title?.userPreferred ?: "Unknown Title",
            fontFamily = luckyfontfamily,
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 8.dp, top = 20.dp)
        )
        GlideImage(
            imageModel = media.coverImage?.extraLarge ?: "",
            contentDescription = "Anime Cover Image",
            modifier = Modifier
                .size(400.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Description",
            fontSize = 20.sp,
            fontFamily = luckyfontfamily
        )
        Text(
            text = stripHtml(media.description ?: "No description available."),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Row {
                Text(text = stringResource(id = R.string.avg_score),
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = 20.dp))

                ScoreCircle(score = media.averageScore)

        }
        Text(text = stringResource(id = R.string.release) + media.nextAiringEpisode?.airingAt?.let { formatTimestamp(it) })

       
    }
}

@Composable
fun TrailerButton(trailerUrl: String, context: android.content.Context) {
    Button(
        onClick = {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl)))
        },
        colors = ButtonDefaults.buttonColors(Color.Black),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(stringResource(id = R.string.watch_trail), color = Color.White)
    }
}

@Composable
fun AddToFavoritesButton(detailsViewModel: DetailsViewModel, userRepository: UserRepository,
                         navigateToBack: () -> Unit) {
        Button(
            onClick = {
                    detailsViewModel.addToFavorites(userRepository)
                    navigateToBack()
                },
            colors = ButtonDefaults.buttonColors(Color.Black),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.add_fav),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
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

