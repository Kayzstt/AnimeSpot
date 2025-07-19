package com.example.animespot

import com.example.animespot.screens.favorite.FavoriteViewModel
import FavoritesScreen
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.animespot.api.RetrofitApi
import com.example.animespot.database.Repository
import com.example.animespot.repository.AnimeRepository
import com.example.animespot.screens.login.AuthViewModel
import com.example.animespot.repository.UserRepository
import com.example.animespot.screens.AnimeSpotScreen
import com.example.animespot.screens.about.AboutScreen
import com.example.animespot.screens.detail.DetailScreen
import com.example.animespot.screens.detail.DetailsViewModel
import com.example.animespot.screens.home.HomeScreen
import com.example.animespot.screens.home.HomeViewModel
import com.example.animespot.screens.home.HomeViewModelFactory
import com.example.animespot.screens.login.LoginScreen
import com.example.animespot.screens.login.RegisterScreen
import com.example.animespot.screens.profile.ProfileScreen
import com.example.animespot.screens.profile.ProfileViewModel
import com.example.animespot.screens.search.SearchScreen
import com.example.animespot.screens.search.SearchViewModel
import com.example.animespot.ui.theme.AnimeSpotTheme



class MainActivity : ComponentActivity() {
    private lateinit var authViewModel: AuthViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var detailViewModel: DetailsViewModel
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var profileViewModel: ProfileViewModel


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val userRepository = UserRepository()
            val animeRepository = AnimeRepository(RetrofitApi.aniListAPI)
            Repository.initDatabase(applicationContext)
            favoriteViewModel = FavoriteViewModel(Repository)
            authViewModel = AuthViewModel(userRepository)
            profileViewModel = ProfileViewModel()
            val homeViewModelFactory = HomeViewModelFactory(animeRepository)
            detailViewModel = DetailsViewModel(Repository,userRepository)
            searchViewModel = SearchViewModel(animeRepository)
            homeViewModel = ViewModelProvider(this,homeViewModelFactory)[HomeViewModel::class.java]
            NavHostApp(authViewModel,homeViewModel,detailViewModel,searchViewModel,favoriteViewModel,userRepository,profileViewModel)
        }
    }
}
@Composable
fun BottomBar(navController: NavHostController) {
    BottomAppBar{
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { navController.navigate(AnimeSpotScreen.Home.name) },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(Icons.Filled.Home, contentDescription = "Home")
            }
            IconButton(
                onClick = { navController.navigate(AnimeSpotScreen.Search.name) },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
            IconButton(
                onClick = { navController.navigate(AnimeSpotScreen.Favorite.name) },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
            }
            IconButton(
                onClick = { navController.navigate(AnimeSpotScreen.Profile.name) },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(Icons.Filled.Person, contentDescription = "Profile")
            }

            IconButton(
                onClick = { navController.navigate(AnimeSpotScreen.About.name) },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(Icons.Filled.Info, contentDescription = "About")
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavHostApp(authViewModel: AuthViewModel, homeViewModel: HomeViewModel, detailsViewModel: DetailsViewModel, searchViewModel: SearchViewModel, favoriteViewModel: FavoriteViewModel, userRepository: UserRepository, profileViewModel : ProfileViewModel){
    val navController = rememberNavController()
    val isBottomBarVisible = remember { mutableStateOf(true)}
    LaunchedEffect(Unit) {
        if (authViewModel.getCurrentUser() != null) {
            navController.navigate(AnimeSpotScreen.Home.name) {
                popUpTo(AnimeSpotScreen.Login.name) {
                    inclusive = true
                }
            }
        }
    }
    AnimeSpotTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(bottomBar = {if (isBottomBarVisible.value) {
                BottomBar(navController = navController)
            }
            })
            {it
                NavHost(navController = navController, startDestination = AnimeSpotScreen.Login.name) {
                    composable(AnimeSpotScreen.Login.name) {
                        isBottomBarVisible.value = false
                        LoginScreen(navigateToRegister = { navController.navigate(AnimeSpotScreen.Register.name) }, navigateToHome = {navController.navigate(AnimeSpotScreen.Home.name)},authViewModel)
                    }
                    composable(AnimeSpotScreen.Register.name) {
                        isBottomBarVisible.value = false
                        RegisterScreen(authViewModel,navigateToLogin = {navController.navigate(AnimeSpotScreen.Login.name)})
                    }
                    composable(AnimeSpotScreen.Home.name) {
                        isBottomBarVisible.value = true
                        HomeScreen(homeViewModel, navigateToDetail ={navController.navigate(AnimeSpotScreen.Detail.name)},
                            detailsViewModel = detailsViewModel
                        )
                    }
                    composable(AnimeSpotScreen.Search.name) {
                        isBottomBarVisible.value = true

                        SearchScreen(searchViewModel)
                    }
                    composable(AnimeSpotScreen.Favorite.name) {
                        isBottomBarVisible.value = true

                        FavoritesScreen(favoriteViewModel,userRepository)
                    }
                    composable(AnimeSpotScreen.Profile.name) {
                        isBottomBarVisible.value = true

                        ProfileScreen(authViewModel, navigateToLogin = {navController.navigate(AnimeSpotScreen.Login.name)}, profileViewModel )
                    }
                    composable(AnimeSpotScreen.About.name) {
                        isBottomBarVisible.value = true
                        AboutScreen()
                    }
                    composable(AnimeSpotScreen.Detail.name){
                        DetailScreen(detailsViewModel, userRepository , navigateToBack = {navController.navigateUp()})
                    }
                }

            }

        }
    }

}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}