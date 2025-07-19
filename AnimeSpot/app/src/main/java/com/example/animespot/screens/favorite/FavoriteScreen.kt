import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animespot.R
import com.example.animespot.database.FavoriteAnime
import com.example.animespot.repository.UserRepository
import com.example.animespot.screens.favorite.FavoriteViewModel
import com.example.animespot.screens.utils.stripHtml
import com.example.animespot.ui.theme.luckyfontfamily
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun FavoritesScreen(viewModel: FavoriteViewModel, userRepository: UserRepository) {
    val favorites by viewModel.favorites.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    viewModel.fetchFavorites(userRepository.getCurrentUser()?.uid.toString())

    Surface(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                text = stringResource(id = R.string.your_fav),
                fontWeight = FontWeight.Black,
                fontFamily = luckyfontfamily,
                fontSize = 20.sp
            )
            Column {
                val localFavorites = favorites
                if (isLoading) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator()
                    }
                } else if (localFavorites.isNullOrEmpty()) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(stringResource(id = R.string.no_fav))
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 56.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start
                    ) {
                        items(localFavorites) { anime ->
                            AnimeItem(anime = anime, onDelete = { viewModel.deleteFavorite(anime) })
                        }
                    }
                }
            }
        }
    }
}

    @Composable
    fun AnimeItem(anime: FavoriteAnime, onDelete: () -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                GlideImage(
                    imageModel = anime.imageUrl,
                    contentDescription = "Anime cover image",
                    modifier = Modifier
                        .size(115.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterVertically)
                ) {
                        Text(
                            text = anime.title,
                            fontWeight = FontWeight.Bold,
                            fontFamily = luckyfontfamily,
                            fontSize = 16.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    Text(
                        text = stripHtml(anime.description),
                        fontSize = 13.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    IconButton(onClick = onDelete) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Delete",
                            tint = Color.Red

                        )
                    }
                }

            }

        }

    }

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}
