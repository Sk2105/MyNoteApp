package com.sgtech.noteapp.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sgtech.noteapp.MainViewModel
import com.sgtech.noteapp.data.local.entities.Note
import com.sgtech.noteapp.utils.getFormattedDateAndTime


@Composable
fun NoteCard(
    mainViewModel: MainViewModel,
    note: Note = Note(
        title = "This is a title",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, nisl nec ultricies sodales, nisl nisl ultricies.",
        date = "5 April 2023",
        imgUrl = "",
        isFavorite = true
    ),
    onClick: (note: Note) -> Unit = {}
) {

    var dialogState by remember {
        mutableStateOf(false)
    }
    ElevatedCard(
        onClick = {
            onClick(note)
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = note.title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = if (note.description.length > 100) {
                    AnnotatedString(
                        text = note.description.substring(0, 100)
                    ) +
                            AnnotatedString(
                                text = "...Read More",
                                spanStyle = SpanStyle(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                } else {
                    AnnotatedString(text = note.description)
                },
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = getFormattedDateAndTime(note.date.toLong()),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 4.dp),
                style = MaterialTheme.typography.bodySmall
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                IconButton(onClick = {
                      mainViewModel.updateNote(note.copy(isFavorite = !note.isFavorite))
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = null,
                        tint = if (note.isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                    )
                }
                IconButton(onClick = {
                    dialogState = true
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                    )
                }
            }
        }
    }

    if (dialogState) {
        DeleteDialog(
            onDismiss = { dialogState = false },
            onDelete = {
                mainViewModel.deleteNote(note)
                dialogState = false
            }
        )
    }
}

@Composable
fun DeleteDialog(onDismiss: () -> Unit, onDelete: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            Button(
                onClick = {
                    onDelete()
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                )
            ) {
                Row(horizontalArrangement = Arrangement.Center) {
                    Icon(imageVector = Icons.Rounded.Delete, contentDescription = null)
                    Text(
                        text = "Delete",
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = { onDismiss() }, colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Text(
                    text = "Cancel",
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

            }

        },
        title = {
            Text(
                text = "Delete Note",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        text = {
            Text(
                text = "Are you sure you want to delete this note?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
    )
}