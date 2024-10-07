package com.sgtech.noteapp.presentation.navigation.graph

import kotlinx.serialization.Serializable


sealed interface NavigationGraph {

    @Serializable
    data object Splash : NavigationGraph

    @Serializable
    data object Search : NavigationGraph

    @Serializable
    data object Home : NavigationGraph

    @Serializable
    data object Add : NavigationGraph

    @Serializable
    data class Update(val id: Int? = null) : NavigationGraph

    @Serializable
    data object Favourites : NavigationGraph

    @Serializable
    data object Settings : NavigationGraph

    @Serializable
    data object About : NavigationGraph
}