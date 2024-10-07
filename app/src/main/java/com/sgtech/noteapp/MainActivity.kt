package com.sgtech.noteapp

import android.icu.util.VersionInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sgtech.noteapp.presentation.navigation.AppNavigation
import com.sgtech.noteapp.presentation.navigation.graph.NavigationGraph
import com.sgtech.noteapp.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var route: NavigationGraph = NavigationGraph.Home
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            installSplashScreen()
        } else {
            route = NavigationGraph.Splash
        }
        setContent {

            NoteAppTheme() {

                AppNavigation(route)
            }
        }
    }
}