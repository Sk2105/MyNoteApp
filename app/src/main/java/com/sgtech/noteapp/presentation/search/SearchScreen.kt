package com.sgtech.noteapp.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sgtech.noteapp.MainViewModel
import com.sgtech.noteapp.presentation.home.components.NoteCard
import com.sgtech.noteapp.presentation.navigation.graph.NavigationGraph

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navigator: NavHostController, mainViewModel: MainViewModel) {
    var searchQuery by remember { mutableStateOf("") }
    val localManager = LocalFocusManager.current
    localManager.moveFocus(FocusDirection.Down)
    val notes = mainViewModel.notes.collectAsState().value.filter {
        it.title.contains(searchQuery) || it.description.contains(searchQuery) || it.date.contains(
            searchQuery
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text(text = "Search") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.background,
                        unfocusedBorderColor = MaterialTheme.colorScheme.background,
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        localManager.clearFocus()
                    }),
                    modifier = Modifier
                        .fillMaxWidth()

                )
            }, navigationIcon = {
                IconButton(onClick = { navigator.popBackStack() }) {
                    Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = null)
                }
            })
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .then(
                    if (notes.isEmpty()) {
                        Modifier.fillMaxSize()
                    } else {
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    }
                )
        ) {
            if (notes.isEmpty()) {
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
                        navigator.navigate(NavigationGraph.Add)
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )

                    }
                }

            } else {
                Text(
                    text = "Search Results for `$searchQuery`",
                    style = MaterialTheme.typography.titleMedium
                        .copy(color = MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier.padding(start = 16.dp)

                )

                notes.forEach { note ->
                    if (note.title.contains(searchQuery) || note.description.contains(searchQuery)) {
                        NoteCard(note = note, mainViewModel = mainViewModel) {
                            navigator.navigate(NavigationGraph.Update(note.id))
                        }
                    }
                }
            }


        }
    }
}