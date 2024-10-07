package com.sgtech.noteapp.presentation.favourites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sgtech.noteapp.MainViewModel
import com.sgtech.noteapp.presentation.favourites.components.FavouriteTopBar
import com.sgtech.noteapp.presentation.home.components.NoteCard
import com.sgtech.noteapp.presentation.navigation.graph.NavigationGraph


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteScreen(mainViewModel: MainViewModel, navHostController: NavHostController) {
    val favouriteNotes = mainViewModel.getFavouriteNotes().collectAsState(initial = emptyList())
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topBarState
    )

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FavouriteTopBar(onBack = {
                navHostController.popBackStack()
            }, scrollBehavior = scrollBehavior)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .then(
                    if (favouriteNotes.value.isEmpty()) {
                        Modifier.fillMaxSize()
                    } else {
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    }
                )
        ) {

            if (favouriteNotes.value.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No Favourite Notes",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    FloatingActionButton(onClick = {
                        navHostController.navigate(NavigationGraph.Add)
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )

                    }
                }
            } else {
                favouriteNotes.value.forEach { note ->
                    NoteCard(note = note, mainViewModel = mainViewModel, onClick = {
                        navHostController.navigate(NavigationGraph.Update(note.id))
                    })
                }
            }

        }

    }


}