package com.sgtech.noteapp.presentation.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sgtech.noteapp.utils.getFormatedDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    openSearchScreen: () -> Unit,
    openDrawer: () -> Unit
) {

    TopAppBar(
        scrollBehavior = topAppBarScrollBehavior,
        title = {
            Text(
                text = getFormatedDate(System.currentTimeMillis()),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(align = androidx.compose.ui.Alignment.CenterHorizontally)
            )
        }, modifier = Modifier,
        navigationIcon = {
            IconButton(onClick = {
                openDrawer()
            }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = null)
            }
        }, actions = {
            IconButton(onClick = {
                openSearchScreen()
            }) {
                Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
            }
        })
}