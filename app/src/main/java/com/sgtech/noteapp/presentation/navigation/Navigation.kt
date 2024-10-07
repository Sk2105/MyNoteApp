package com.sgtech.noteapp.presentation.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sgtech.noteapp.MainViewModel
import com.sgtech.noteapp.presentation.add.AddScreen
import com.sgtech.noteapp.presentation.favourites.FavouriteScreen
import com.sgtech.noteapp.presentation.home.HomeScreen
import com.sgtech.noteapp.presentation.navigation.graph.NavigationGraph
import com.sgtech.noteapp.presentation.search.SearchScreen
import com.sgtech.noteapp.presentation.splash.SplashScreen

@Composable
fun AppNavigation(route:NavigationGraph) {
    val viewModel: MainViewModel = hiltViewModel()
    val navigator = rememberNavController()
    NavHost(navController = navigator,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        startDestination = route,
        enterTransition = {
            slideInHorizontally { 2 * it }
        },
        exitTransition = {
            slideOutHorizontally { -2 * it }
        },
        popExitTransition = {
            slideOutHorizontally { 2 * it }
        },
        popEnterTransition = {
            slideInHorizontally { -2 * it }
        })
    {

        composable<NavigationGraph.Splash> {
            SplashScreen(navigator)
        }
        composable<NavigationGraph.Home>() {
            HomeScreen(navigator, viewModel)
        }

        composable<NavigationGraph.Add> {
            AddScreen(navHostController = navigator, mainViewModel = viewModel)
        }

        composable<NavigationGraph.Search> {
            SearchScreen(navigator, mainViewModel = viewModel)
        }

        composable<NavigationGraph.Update> {
            val update: NavigationGraph.Update = it.toRoute()
            AddScreen(id = update.id, navHostController = navigator, mainViewModel = viewModel)
        }

        composable<NavigationGraph.Favourites> {
            FavouriteScreen(mainViewModel = viewModel, navHostController = navigator)
        }


    }
}