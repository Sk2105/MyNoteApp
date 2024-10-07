package com.sgtech.noteapp.presentation.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.sgtech.noteapp.MainViewModel
import com.sgtech.noteapp.presentation.home.components.HomeTopBar
import com.sgtech.noteapp.presentation.home.components.NoteCard
import com.sgtech.noteapp.presentation.navigation.graph.NavigationGraph
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navigator: NavHostController, viewModel: MainViewModel) {

    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        state = topAppBarState
    )
    val scrollState = rememberScrollState()
    val notes = viewModel.notes.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var aboutDialogState by remember {
        mutableStateOf(false)
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerItem(
                    icon = { Icon(Icons.Rounded.Home, contentDescription = null) },
                    label = { Text(text = NavigationGraph.Home.toString()) },
                    selected = true,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Rounded.Search, contentDescription = null) },
                    label = { Text(text = NavigationGraph.Search.toString()) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigator.navigate(NavigationGraph.Search)
                    }
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Rounded.Favorite, contentDescription = null) },
                    label = { Text(text = NavigationGraph.Favourites.toString()) },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigator.navigate(NavigationGraph.Favourites)
                    }
                )


                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                Text(text = "More", modifier = Modifier.padding(16.dp))

                NavigationDrawerItem(
                    icon = { Icon(Icons.Rounded.Info, contentDescription = null) },
                    label = { Text(text = "About") },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }

                        aboutDialogState = true
                    }
                )


                NavigationDrawerItem(
                    icon = { Icon(Icons.Rounded.Star, contentDescription = null) },
                    label = { Text(text = "Rate us") },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        val uri = Uri.parse("https://play.google.com/store/apps/details?id=com.sgtech.noteapp")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        navigator.context.startActivity(intent)
                    }
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Rounded.Email, contentDescription = null) },
                    label = { Text(text = "Feedback") },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        val emailIntent = Intent(
                            Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto", "s.kumar911784@gmail.com", null
                            )
                        )
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "NoteApp Feedback")
                        navigator.context.startActivity(
                            Intent.createChooser(
                                emailIntent,
                                "Send feedback"
                            )
                        )

                    }
                )

                NavigationDrawerItem(
                    icon = { Icon(Icons.Rounded.Share, contentDescription = null) },
                    label = { Text(text = "Share with friends") },
                    selected = false,
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Note App")
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.sgtech.noteapp")
                        navigator.context.startActivity(shareIntent)
                    }
                )


            }
        },
        modifier = Modifier,
        scrimColor = MaterialTheme.colorScheme.background,
        drawerState = drawerState
    ) {
        Scaffold(modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                HomeTopBar(topAppBarScrollBehavior = scrollBehavior, openSearchScreen = {
                    navigator.navigate(NavigationGraph.Search)
                }) {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    expanded = !scrollState.isScrollInProgress,
                    onClick = { navigator.navigate(NavigationGraph.Add) },
                    icon = { Icon(Icons.Filled.Add, "Extended floating action button.") },
                    text = { Text(text = "Add Note") },
                )
            }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
                    .then(
                        if (notes.value.isEmpty()) {
                            Modifier.fillMaxSize()
                        } else {
                            Modifier.verticalScroll(scrollState)
                        }
                    )
            ) {
                if (notes.value.isEmpty()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "No Notes Found",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        FloatingActionButton(onClick = {
                            navigator.navigate(NavigationGraph.Add)
                        }) {
                            Icon(Icons.Filled.Add, contentDescription = null)
                        }
                    }

                } else {
                    notes.value.forEach { note ->
                        NoteCard(note = note, mainViewModel = viewModel) {
                            navigator.navigate(NavigationGraph.Update(note.id))
                        }
                    }
                }


            }

            if (aboutDialogState) {
                AboutDialog {
                    aboutDialogState = false
                }
            }

        }


    }
}

@Composable
fun AboutDialog(onConfirm: () -> Unit) {

    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                text = "ℹ️ About",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(
                    text = "NoteApp 📝 is a simple note taking app built with Jetpack Compose and Hilt. It is intended for learning 📖 purposes only. No personal or financial information is stored. ",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Thank you ❤  ️for using Note App.",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "All rights reserved. ",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "👨‍💻 - Sachin Kumar",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }


        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
            }) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}