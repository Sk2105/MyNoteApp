package com.sgtech.noteapp.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sgtech.noteapp.R
import com.sgtech.noteapp.presentation.navigation.graph.NavigationGraph
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController)
{
    LaunchedEffect(key1 = Unit) {
        delay(3000)
        navHostController.navigate(NavigationGraph.Home) {
            popUpTo(NavigationGraph.Splash) {
                inclusive = true
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
    {
        Image(
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = null,
            modifier = Modifier.size(128.dp).background(Color.Black, shape = RoundedCornerShape(30.dp))
        )

    }



}